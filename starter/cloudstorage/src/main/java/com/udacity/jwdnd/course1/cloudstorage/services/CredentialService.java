package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private UserService userService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public Integer addCreds(Credential cred){
        return credentialMapper.insert(cred);
    }

    public void deleteFCreds(Integer credentialid) {
        credentialMapper.deleteCreds(credentialid);
    }

    public Credential getCreds(String url) {
        return credentialMapper.getCreds(url);
    }

    public List<Credential> getAllCreds(Integer userid) {
        return credentialMapper.getAllCredentials(userid);
    }

    public boolean credsForUrlExist(String url, Integer userId) {
        return (this.credentialMapper.getUrlFoUser(url, userId) != null);
    }
}
