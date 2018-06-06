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
import java.util.List;
import java.util.Map;

import co.za.bluemarble.data.local.EpicLocalDataSource;
import co.za.bluemarble.data.remote.EpicRemoteDataSource;
import co.za.bluemarble.features.GetAllImages.domain.model.EarthInfo;
import io.reactivex.Observable;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
//@Singleton
public class EpicRepository implements EpicDataSource {

    private static EpicRepository INSTANCE = null;



    private final EpicRemoteDataSource mRemoteDataSource;

    private final EpicLocalDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, EarthInfo> mCachedEarthInfo;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    public EpicRepository(EpicRemoteDataSource mRemoteDataSource, EpicLocalDataSource mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }



    //check for data in the local repository if empty call to the network
    @Override
    public Observable<List<EarthInfo>> getEarthInfo(String date) {

        // Respond immediately with cache if available and not dirty
//        if (mCachedEarthInfo != null && !mCacheIsDirty) {
//           // callback.onTasksLoaded(new ArrayList<>(mCachedEarthInfo.values()));
//            return;
//        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
          return mRemoteDataSource.getEarthInfo(date);
        } else {
            // Query the local storage if available. If not, query the network.
            return mLocalDataSource.getEarthInfo(date);

                }
    }

    }