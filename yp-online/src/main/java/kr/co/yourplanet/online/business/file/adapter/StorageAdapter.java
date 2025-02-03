package kr.co.yourplanet.online.business.file.adapter;

import java.net.URL;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageAdapter {

    URL upload(MultipartFile file, String fileKey);

    URL generatePresignedUrl(String fileKey);

    List<URL> generatePresignedUrls(List<String> fileKey);
}
