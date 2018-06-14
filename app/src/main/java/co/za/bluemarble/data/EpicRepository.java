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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Singleton;

import co.za.bluemarble.data.exception.DatabaseException;
import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
@Singleton
public class EpicRepository implements EpicDataSource {

    private static EpicRepository INSTANCE = null;


    private final EpicRemoteDataSource mRemoteDataSource;

    private final EpicLocalDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    public Map<String, EarthInfo> mCachedEarthInfo;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    public EpicRepository(EpicRemoteDataSource mRemoteDataSource, EpicLocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }


    //check for data in the local repository if empty call to the network
    @Override
    public void getEarthInfo(String date, final LoadInfoCallback callback) {

        // Respond immediately with cache if available and not dirty
        if (mCachedEarthInfo != null && !mCacheIsDirty) {
            callback.onDataLoaded(new ArrayList<>(mCachedEarthInfo.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getAndSaveRemoteEarthInfo(date, callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mLocalDataSource.getEarthInfo(date, new LoadInfoCallback() {
                @Override
                public void onDataLoaded(List<EarthInfo> tasks) {
                    refreshCache(tasks);
                    callback.onDataLoaded(new ArrayList<>(mCachedEarthInfo.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getAndSaveRemoteEarthInfo(date, callback);
                }
            });

        }
    }

    private void getAndSaveRemoteEarthInfo(String date, @NonNull final LoadInfoCallback callback) {
        mRemoteDataSource.getEarthInfo(date, new LoadInfoCallback() {
            @Override
            public void onDataLoaded(List<EarthInfo> info) {
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

    private void refreshCache(List<EarthInfo> listInfo) {
        if (mCachedEarthInfo == null) {
            mCachedEarthInfo = new LinkedHashMap<>();
        }
        mCachedEarthInfo.clear();
        for (EarthInfo info : listInfo) {
            mCachedEarthInfo.put(info.getIdentifier(), info);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<EarthInfo> marbles) {
        mLocalDataSource.deleteAllInfo();
        for (EarthInfo info : marbles) {
            mLocalDataSource.saveTask(info);
        }
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
    public void saveTask(EarthInfo marbles) {
        checkNotNull(marbles);
        mRemoteDataSource.saveTask(marbles);
        mLocalDataSource.saveTask(marbles);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedEarthInfo == null) {
            mCachedEarthInfo = new LinkedHashMap<>();
        }
        mCachedEarthInfo.put(marbles.getIdentifier(), marbles);
    }


    /**
     * Returns the single instance of this class, creating it if necessary.
     *used for testing
     * @param remoteDataSource the backend data source
     * @param localDataSource  the device storage data source
     * @return the {@link EpicRepository} instance
     */
    public static EpicRepository getInstance(EpicRemoteDataSource remoteDataSource,
                                             EpicLocalDataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new EpicRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(EpicRemoteDataSource, EpicLocalDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


}