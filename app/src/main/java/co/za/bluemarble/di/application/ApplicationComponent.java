package co.za.bluemarble.di.application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {
  //  public PresentationComponent newPresentationComponent(PresentationModule presentationModule);
}
