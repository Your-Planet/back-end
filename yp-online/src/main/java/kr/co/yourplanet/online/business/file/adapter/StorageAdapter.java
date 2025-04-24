package kr.co.yourplanet.online.business.file.adapter;

import java.net.URL;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public interface StorageAdapter {

    URL upload(MultipartFile file, String fileKey);

    URL getUploadUrl(String fileKey, MediaType mediaType);

    URL getDownloadUrl(String fileKey, long expireTime);

    URL getPublicUrl(String fileKey);
}
