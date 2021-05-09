package Utils;

import android.content.Context;
import android.media.Image;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Date;

public class ImageHander {

    private StorageReference storage;

    public  ImageHander(){
        storage = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File image) {
        byte[] imageByte = CompressorBitmapImage.getImage(context, image.getPath(), 500, 500);
        StorageReference storageReference = storage.child(new Date() + ".jpg");
        storage = storageReference;
        UploadTask task = storageReference.putBytes(imageByte);

        return task;
    }

    public StorageReference getStorage() {
        return storage;
    }
}
