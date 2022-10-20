package service;

import Model.SocialAccount;

public interface SocialService {

    String getAuthorizeUrl();

    SocialAccount getSocialAccount(String authToken);
}
