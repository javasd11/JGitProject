package com.vados;

import org.eclipse.jgit.api.Git;

import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

//import org.testng.annotations.Test;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GitAuthTesting {
    final String REMOTE_URL = "https://github.com/javasd11/testJGit.git";
    final String USER_NAME = "javasd11";
    final String USER_PASSORD = "g1x2d5k4i9";
    final String REPO_PATH = "D://Inbox//testGitRepo";
    final String FILE_NAME = "Newfile";

    @Autowired
    private UserSettings userSettings;

    @Autowired
    private GitJob gitJob;

    @Before
    public void init() {
        userSettings.setRepoURL(REMOTE_URL);
        userSettings.setLocalRepoPath(REPO_PATH);
        userSettings.setPassword(USER_PASSORD);
        userSettings.setUserName(USER_NAME);
        removeGitFolder();
    }

//    @Test
//    public void testGetGit() {
//        Git git = gitJob.getRepository();
//        assertNotNull("Repository not initialized", git);
//    }
//
//    @Test
//    public void testInitGit() {
//        Git git = gitJob.initRepository();
//        git.getRepository().getDirectory();
//        File gitDir = new File(REPO_PATH + "/.git");
//        assertNotNull(git);
//        assertTrue("Folder git does not exist", gitDir.exists());
//        assertTrue("Folder git not found", gitDir.isDirectory());
//    }
//
//    @Test
//    public void testCloneGit() {
//        Git git = gitJob.cloneRepository();
//        File gitDir = new File(REPO_PATH);
//        List<File> foldersList = Arrays.asList(gitDir.listFiles());
//        assertNotNull(git);
//        boolean fileResultClone = false;
//        for (File fileItem : foldersList) {
//            if (!fileItem.getName().equals(".git")) {
//                fileResultClone = true;
//                break;
//            }
//        }
//        assertTrue("files were not cloned from repository", fileResultClone);
//        assertTrue("Folder git does not exist", gitDir.exists());
//        assertTrue("Folder git not found", gitDir.isDirectory());
//    }

    @Test
    public void testAddToGit() {
        gitJob.initRepository();
        crateNewFile();
        ArrayList<String> filesList = new ArrayList<>();
        filesList.add(FILE_NAME);
        gitJob.addJob(filesList);

        Git git = gitJob.getRepository();
        try {
            Status status = git.status().call();
            Set<String> addedFiles = status.getAdded();
            assertTrue("file not found",addedFiles.contains(FILE_NAME));
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        git.close();
    }

//    @Test
//    public void testAddToGit() {
//        gitJob.initRepository();
//        crateNewFile();
//        ArrayList<String> filesList = new ArrayList<>();
//        filesList.add("newFile.txt");
//        gitJob.addJob(filesList);
//
//        Git git = gitJob.getRepository();
//        try {
//            Status status = git.status().call();
//            Set<String> addedFiles = status.getAdded();
//            assertTrue("file not found",addedFiles.contains(FILE_NAME));
//        } catch (GitAPIException e) {
//            e.printStackTrace();
//        }
//        git.close();
//    }
//
//    @Test
//    public void testCreateBrunch(){
//        Git git = gitJob.initRepository();
//        gitJob.getRepository();
//        gitJob.createBrunch("UAT");
//        git.push();
//        git.close();
//    }

    public void crateNewFile() {
        File f = new File(REPO_PATH + "/"+FILE_NAME);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeGitFolder() {
        try {
            FileUtils.forceDelete(new File(REPO_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
