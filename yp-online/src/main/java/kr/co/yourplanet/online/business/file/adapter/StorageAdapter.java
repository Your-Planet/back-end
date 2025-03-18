package kr.co.yourplanet.online.business.file.adapter;

import java.net.URL;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.model.FileMetadata;

public interface StorageAdapter {

    URL upload(MultipartFile file, String fileKey);

    URL generatePresignedUrl(String fileName, String fileKey, MediaType mediaType);

    FileMetadata getMetadata(String fileUrl);
}
