package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer addCreds(Credential cred){
        String password = cred.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = this.encryptionService.encryptValue(password, encodedKey);
        cred.setPassword(encryptedPassword);
        cred.setKey(encodedKey);
        return this.credentialMapper.insert(cred);
    }

    public void deleteCred(Integer credentialid) {
        this.credentialMapper.deleteCreds(credentialid);
    }

    public Credential getCreds(String url) {
        return this.credentialMapper.getCreds(url);
    }

    public List<Credential> getAllCreds(Integer userid) {
        return this.credentialMapper.getAllCredentials(userid);
    }

    public boolean credsForUrlExist(String url, Integer userId) {
        return (this.credentialMapper.getUrlFoUser(url, userId) != null);
    }

    public void updateCred(Credential newCredential) {
        String password = newCredential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = this.encryptionService.encryptValue(password, encodedKey);
        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);
        this.credentialMapper.updateCred(newCredential);
    }
}
