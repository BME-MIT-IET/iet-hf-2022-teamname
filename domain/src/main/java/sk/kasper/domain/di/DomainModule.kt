package sk.kasper.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sk.kasper.domain.usecase.launchdetail.*
import sk.kasper.domain.usecase.launchdetail.impl.*

@InstallIn(SingletonComponent::class)
@Module
class DomainModule {

    @Provides
    internal fun providesGetPhotos(getPhotosImpl: GetPhotosImpl): GetPhotos = getPhotosImpl

    @Provides
    internal fun providesGetFalconCore(getFalconCoreImpl: GetFalconCoreImpl): GetFalconCore = getFalconCoreImpl

    @Provides
    internal fun providesGetLaunch(getLaunchImpl: GetLaunchImpl): GetLaunch = getLaunchImpl

    @Provides
    internal fun providesGetLaunchSite(getLaunchSiteImpl: GetLaunchSiteImpl): GetLaunchSite = getLaunchSiteImpl

    @Provides
    internal fun providesGetOrbit(getOrbitImpl: GetOrbitImpl): GetOrbit = getOrbitImpl

    @Provides
    internal fun providesGetRocketForLaunch(getRocketForLaunchImpl: GetRocketForLaunchImpl): GetRocketForLaunch =
        getRocketForLaunchImpl

}