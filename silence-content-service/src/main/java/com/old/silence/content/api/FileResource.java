package com.old.silence.content.api;



/**
 * @author MurrayZhang
 */
//@RestController
//@RequestMapping("/api/v1")
public class FileResource {

    /*private final FileStorageService storageService;
    private final PdfProcessingService pdfService;
    private final VectorSearchService vectorService;
    private final FullTextSearchService searchService;
    private final FileMetadataRepository fileMetadataRepository;

    public FileResource(FileStorageService storageService, PdfProcessingService pdfService,
                        VectorSearchService vectorService, FullTextSearchService searchService,
                        FileMetadataRepository fileMetadataRepository) {
        this.storageService = storageService;
        this.pdfService = pdfService;
        this.vectorService = vectorService;
        this.searchService = searchService;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @PostMapping("/files/upload")
    public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) {
        try {
            // 1. 处理PDF
            List<String> pages = pdfService.extractTextFromPdf(file.getInputStream());
            List<String> chunks = pdfService.chunkText(pages);

            // 2. 存储到MinIO
            String objectName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String presignedUrl = storageService.uploadFile(file.getInputStream(), objectName);

            // 3. 保存元数据
            var metadata = new FileMetadata();
            metadata.setOriginalName(file.getOriginalFilename());
            metadata.setMinioObjectName(objectName);
            metadata.setFileSize(file.getSize());
            metadata.setContentType(file.getContentType());
            fileMetadataRepository.create(metadata);

            // 4. 索引到搜索引擎（异步）
            CompletableFuture.runAsync(() -> {
                searchService.indexDocument(metadata.getId(), file.getOriginalFilename(), chunks);
                // 修改为新的调用方式
                vectorService.insertEmbeddings(chunks, String.valueOf(metadata.getId()), file.getOriginalFilename());
            });

            return ResponseEntity.ok(Map.of(
                    "message", "文件上传成功",
                    "fileId", Objects.requireNonNull(metadata.getId()),
                    "chunks", chunks.size(),
                    "readUrl", presignedUrl
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "文件处理失败: " + e.getMessage()));
        }
    }

    @GetMapping("/files/vector-search")
    public ResponseEntity<?> vectorSearch(@RequestParam String query,
                                          @RequestParam(defaultValue = "5") int topK) {
        try {
            List<Map<String, Object>> results = vectorService.similaritySearch(query, topK);
            return ResponseEntity.ok(Map.of("query", query, "results", results));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "搜索失败: " + e.getMessage()));
        }
    }*/
}
