package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Integer addCreds(Credential cred){
        return credentialMapper.insert(cred);
    }

    public void deleteCred(Integer credentialid) {
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

    public void updateNote(Credential newCredential) {
        credentialMapper.updateCred(newCredential);
    }
}
