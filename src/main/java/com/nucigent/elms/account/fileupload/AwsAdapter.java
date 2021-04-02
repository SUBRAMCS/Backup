package com.nucigent.elms.account.fileupload;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;


@Component
public class AwsAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AwsAdapter.class);

	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private  AmazonS3 amazonS3Client;

	public URL storeObjectInS3(MultipartFile file, String fileName, String contentType) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(file.getSize());
		//TODO add cache control
		try {
			amazonS3Client.putObject(bucketName, fileName,file.getInputStream(), objectMetadata);
		} catch(AmazonClientException |IOException exception) {
			throw new RuntimeException("Error while uploading file.");
		}
		return amazonS3Client.getUrl(bucketName, fileName);
	}

	public S3Object fetchObject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
		}catch (AmazonServiceException serviceException) {
			throw new RuntimeException("Error while streaming File.");
		} catch (AmazonClientException exception) {
			throw new RuntimeException("Error while streaming File.");
		}
		return s3Object;
	}

	public byte[] download(String key) throws IOException {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);

		S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

		S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

		byte[] bytes = IOUtils.toByteArray(objectInputStream);

		String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		httpHeaders.setContentLength(bytes.length);
		httpHeaders.setContentDispositionFormData("attachment", fileName);

		return bytes;
	}

	public void deleteObject(String key) {
		try {
			amazonS3Client.deleteObject(bucketName, key);
		}catch (AmazonServiceException serviceException) {
			logger.error(serviceException.getErrorMessage());
		} catch (AmazonClientException exception) {
			logger.error("Error while deleting File.");
		}
	}

}
