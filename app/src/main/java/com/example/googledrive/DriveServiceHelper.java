package com.example.googledrive;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private Drive mDriveService;


    public DriveServiceHelper(Drive mDriveService){
        this.mDriveService = mDriveService;
    }

    public Task<String> creafile(String filePath){
        return  Tasks.call(mExecutor,()->{

            File fileMetaData = new File();
            fileMetaData.setName("MyFileSubido");

            java.io.File file = new java.io.File(filePath);

            FileContent mediaContent = new FileContent("application/jpg",file);

            File myfile = null;
            try{
                myfile = mDriveService.files().create(fileMetaData,mediaContent).execute();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(myfile == null){
                throw new IOException("NULL resul when requesting file creation");
            }
            return myfile.getId();

        });
    }
}
