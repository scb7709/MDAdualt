package com.alibaba.sdk.android.oss.common;

import com.alibaba.sdk.android.oss.common.utils.HttpHeaders;

public interface OSSHeaders extends HttpHeaders {

    String OSS_PREFIX = "x-oss-";
    String OSS_USER_METADATA_PREFIX = "x-oss-meta-";

    String OSS_CANNED_ACL = "x-oss-acl";
    String STORAGE_CLASS = "x-oss-storage-class";
    String OSS_VERSION_ID = "x-oss-version-id";

    String OSS_SERVER_SIDE_ENCRYPTION = "x-oss-server-side-encryption";

    String GET_OBJECT_IF_MODIFIED_SINCE = "If-Modified-Since";
    String GET_OBJECT_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    String GET_OBJECT_IF_MATCH = "If-Match";
    String GET_OBJECT_IF_NONE_MATCH = "If-None-Match";

    String HEAD_OBJECT_IF_MODIFIED_SINCE = "If-Modified-Since";
    String HEAD_OBJECT_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
    String HEAD_OBJECT_IF_MATCH = "If-Match";
    String HEAD_OBJECT_IF_NONE_MATCH = "If-None-Match";

    String COPY_OBJECT_SOURCE = "x-oss-copy-source";
    String COPY_SOURCE_RANGE = "x-oss-copy-source-range";
    String COPY_OBJECT_SOURCE_IF_MATCH = "x-oss-copy-source-if-match";
    String COPY_OBJECT_SOURCE_IF_NONE_MATCH = "x-oss-copy-source-if-none-match";
    String COPY_OBJECT_SOURCE_IF_UNMODIFIED_SINCE = "x-oss-copy-source-if-unmodified-since";
    String COPY_OBJECT_SOURCE_IF_MODIFIED_SINCE = "x-oss-copy-source-if-modified-since";
    String COPY_OBJECT_METADATA_DIRECTIVE = "x-oss-metadata-directive";

    String OSS_HEADER_REQUEST_ID = "x-oss-request-id";

    String ORIGIN = "origin";
    String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    String ACCESS_CONTROL_REQUEST_HEADER = "Access-Control-Request-Headers";

    String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
    String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    String ACCESS_CONTROL_EXPOSE_HEADERS ="Access-Control-Expose-Headers";
    String ACCESS_CONTROL_MAX_AGE ="Access-Control-Max-Age";

    String OSS_SECURITY_TOKEN = "x-oss-security-token";

    String OSS_NEXT_APPEND_POSITION = "x-oss-next-append-position";
    String OSS_HASH_CRC64_ECMA = "x-oss-hash-crc64ecma";
    String OSS_OBJECT_TYPE = "x-oss-object-type";
}
