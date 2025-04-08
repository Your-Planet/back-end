package kr.co.yourplanet.online.infra.aws;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.adapter.StorageAdapter;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3StorageAdapter implements StorageAdapter {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${spring.cloud.config.server.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.config.server.aws.s3.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.config.server.aws.s3.expire-time.upload}")
    private long uploadExpireTime;

    @Override
    public URL upload(MultipartFile file, String fileKey) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .contentType(file.getContentType())
                .bucket(bucket)
                .key(fileKey)
                .build();

        try {
            s3Client.putObject(
                    objectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return getS3FileUrl(fileKey);
        } catch (IOException e) {
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "파일 읽기에 실패했습니다.", true, e);
        } catch (Exception e) {
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.", true, e);
        }
    }

    @Override
    public URL getUploadUrl(String fileKey, MediaType mediaType) {
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofSeconds(uploadExpireTime))
                .putObjectRequest(b -> b.bucket(bucket)
                        .key(fileKey)
                        .contentType(mediaType.toString()))
                .build();

        PresignedPutObjectRequest request = s3Presigner.presignPutObject(presignRequest);
        return request.url();
    }

    @Override
    public URL getDownloadUrl(String fileKey, long expireTime) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(expireTime))
                .getObjectRequest(b -> b.bucket(bucket)
                        .key(fileKey)
                        .responseContentDisposition("attachment"))
                .build();

        PresignedGetObjectRequest request = s3Presigner.presignGetObject(presignRequest);
        return request.url();
    }

    @Override
    public URL getPublicUrl(String fileKey) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(b -> b.bucket(bucket)
                        .key(fileKey))
                .build();

        PresignedGetObjectRequest request = s3Presigner.presignGetObject(presignRequest);
        return request.url();
    }

    private URL getS3FileUrl(String fileKey) {
        try {
            return new URL(endpoint + fileKey);
        } catch (MalformedURLException e) {
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "잘못된 형식의 URL 변환에 실패했습니다.", true, e);
        }
    }
}
