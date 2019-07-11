package com.example.runningtrial.fragments;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.runningtrial.R;
import com.example.runningtrial.base.FragmentBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ShowPdfFragment extends FragmentBasic {
    public String TAG = getClass().getSimpleName();
    private static final String assetPdfNameKey = "assetPdfName";
    private String assetPdfName;
    private ImageView ivPdfView;

    public ShowPdfFragment() {
        // Required empty public constructor
    }

    public static ShowPdfFragment newInstance(String assetPdfName) {
        ShowPdfFragment fragment = new ShowPdfFragment();
        Bundle args = new Bundle();
        args.putString(assetPdfNameKey, assetPdfName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assetPdfName = getArguments().getString(assetPdfNameKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_show_pdf, container, false);
        // copy pdf file to Cache
        // copyPdfToCache();
        // show pdf
        ivPdfView = rootView.findViewById(R.id.ivPdfView);
        try {
            openPdfFromRaw(ivPdfView, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void copyPdfToCache() {
        InputStream input = null;
        FileOutputStream output = null;

        File fileTemp = new File(getContext().getCacheDir(), assetPdfName);
        // Log.d(TAG, getContext().getCacheDir().getAbsolutePath());
        if (fileTemp.length() > 0) {
            Log.d(TAG, "target file exist");
            return;
        }
        try {
            input = getContext().getAssets().open(assetPdfName);
        } catch (IOException e) {
            Log.d(TAG, "IOException from input");
        }
        try {
            output = new FileOutputStream(fileTemp);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "IOException from output");
        }

        // start copy
        byte[] buffer = new byte[1024];
        int size;
        // Copy the entire contents of the file
        try {
            while ((size = input.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            //Close the buffer
            input.close();
            output.close();
        }catch(IOException err){
            Log.d(TAG,"IOException in copyPdfToCache");
        }
    }

    void openPdfFromRaw(ImageView imageView, int pageNumber) throws IOException {
        PdfRenderer mPdfRenderer = null;
        PdfRenderer.Page mPdfPage = null;

        copyPdfToCache();
        File fileCopy = new File(getContext().getCacheDir(), assetPdfName);

        // We get a page from the PDF doc by calling 'open'
        ParcelFileDescriptor fileDescriptor =
                ParcelFileDescriptor.open(fileCopy,
                        ParcelFileDescriptor.MODE_READ_ONLY);

        mPdfRenderer = new PdfRenderer(fileDescriptor);
        mPdfPage = mPdfRenderer.openPage(pageNumber);

        // Create a new bitmap and render the page contents into it
        Bitmap bitmap = Bitmap.createBitmap(mPdfPage.getWidth(),
                mPdfPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        mPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Set the bitmap in the ImageView
        imageView.setImageBitmap(bitmap);
    }
}
