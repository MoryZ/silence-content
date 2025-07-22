package com.old.silence.content.infrastructure.persistence.callback;


/**
 * @author MurrayZhang
 */
/*public class ContentTagAfterConvert implements AfterConvertCallback<ContentTag> {


    private final MinioClient minioClient;

    public ContentTagAfterConvert(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @NotNull
    @Override
    public ContentTag onAfterConvert(ContentTag contentTag) {
        if (StringUtils.isNotBlank(contentTag.getIconReference())) {
            String objectName = contentTag.getIconReference();
            GetPresignedObjectUrlArgs args = (GetPresignedObjectUrlArgs) ((GetPresignedObjectUrlArgs.Builder) ((GetPresignedObjectUrlArgs.Builder) GetPresignedObjectUrlArgs.builder()
                    .bucket("silence-content-dmz-stg")).object(objectName)).method(Method.GET).build();

            try {
                var presignedObjectUrl = this.minioClient.getPresignedObjectUrl(args);
                contentTag.setIconReference(presignedObjectUrl);
            } catch (InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException |
                     IOException | NoSuchAlgorithmException | XmlParserException | ServerException |
                     ErrorResponseException e) {
                throw new RuntimeException(e);
            }


        }
        return contentTag;
    }
}*/
