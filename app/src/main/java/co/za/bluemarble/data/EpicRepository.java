/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.za.bluemarble.data;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.annotation.NonNull;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoObj;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfoPojos;
import co.za.bluemarble.features.common.ImageLoader;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class EpicRepository implements EpicDataSource {

    private static EpicRepository INSTANCE = null;

    private final EpicRemoteDataSource mRemoteDataSource;

    private final EpicLocalDataSource mLocalDataSource;

    Context mContext;
    ImageLoader imageLoader;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    public Map<String, EarthInfoPojos> mCachedEarthInfo;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    public EpicRepository(EpicRemoteDataSource mRemoteDataSource,
                          EpicLocalDataSource mLocalDataSource, ImageLoader imageLoader
                        //  Context context
    ) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
      //  this.mContext = context;
        this.imageLoader = imageLoader;
    }


    //check for data in the local repository if empty call to the network
    @Override
    public void getEarthInfo(ImageLoader imageLoader, String date, final LoadInfoCallback callback) {

        // Respond immediately with cache if available and not dirty
        if (mCachedEarthInfo != null && !mCacheIsDirty) {
            callback.onDataLoaded(new ArrayList<>(mCachedEarthInfo.values()));
            return;
        }

        if (mCacheIsDirty) {
         //    If the cache is dirty we need to fetch new data from the network.
            getAndSaveRemoteEarthInfo(imageLoader, date, callback);
        }
        else {
            // Query the local storage if available. If not, query the network.
            mLocalDataSource.getEarthInfo(imageLoader, date, new LoadInfoCallback() {
                @Override
                public void onDataLoaded(List<EarthInfoPojos> tasks) {
                    refreshCache(tasks);
                    callback.onDataLoaded(new ArrayList<>(mCachedEarthInfo.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getAndSaveRemoteEarthInfo(imageLoader,date, callback);
                }
            });

        }
    }

    private void getAndSaveRemoteEarthInfo(ImageLoader loader, String date, @NonNull final LoadInfoCallback callback) {
        mRemoteDataSource.getEarthInfo(loader, date, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<EarthInfoPojos> info) {
                refreshCache(info);
                refreshLocalDataSource(info);
                callback.onDataLoaded(new ArrayList<>(mCachedEarthInfo.values()));
            }
            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<EarthInfoPojos> listInfo) {
        if (mCachedEarthInfo == null) {
            mCachedEarthInfo = new LinkedHashMap<>();
        }
        mCachedEarthInfo.clear();
        for (EarthInfoPojos info : listInfo) {
            mCachedEarthInfo.put(info.getIdentifier(), info);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<EarthInfoPojos> marbles) {
        mLocalDataSource.deleteAllInfo();
        List<EarthInfoObj> marbless =  new ArrayList<>(convertSchemaToEntity(marbles));
        for (EarthInfoObj info : marbless) {
            mLocalDataSource.saveTask(info);
        }
    }

    private List<EarthInfoObj> convertSchemaToEntity(List<EarthInfoPojos> earthInfoSchema) {
        List<EarthInfoObj> info = new ArrayList<>(earthInfoSchema.size());
        for (EarthInfoPojos schema : earthInfoSchema) {
            info.add(new EarthInfoObj(schema.getIdentifier(),schema.getCaption(),schema.getImage(),
                    schema.getVersion(), schema.getDate(), schema.getEnhancedEarthImage()));
        }
        return info;
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }


    @Override
    public void deleteAllInfo() {
        mRemoteDataSource.deleteAllInfo();
        mLocalDataSource.deleteAllInfo();

        if (mCachedEarthInfo == null) {
            mCachedEarthInfo = new LinkedHashMap<>();
        }
        mCachedEarthInfo.clear();
    }

    @Override
    public void saveTask(EarthInfoObj marbles) {
        checkNotNull(marbles);
        mRemoteDataSource.saveTask(marbles);
        mLocalDataSource.saveTask(marbles);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedEarthInfo == null) {
            mCachedEarthInfo = new LinkedHashMap<>();
        }

        EarthInfoPojos schema = new EarthInfoPojos(marbles.getIdentifier(),
                marbles.getCaption(),marbles.getImage(),
                marbles.getVersion(), marbles.getDate(), marbles.getEnhanced_images());
        mCachedEarthInfo.put(marbles.getIdentifier(), schema);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *used for testing
     * @param remoteDataSource the backend data source
     * @param localDataSource  the device storage data source
     * @return the {@link EpicRepository} instance
     */
    public static EpicRepository getInstance(EpicRemoteDataSource remoteDataSource,
                                             EpicLocalDataSource localDataSource, ImageLoader imageLoader) {
        if (INSTANCE == null) {
            INSTANCE = new EpicRepository(remoteDataSource, localDataSource, imageLoader);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(EpicRemoteDataSource, EpicLocalDataSource, ImageLoader)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


}