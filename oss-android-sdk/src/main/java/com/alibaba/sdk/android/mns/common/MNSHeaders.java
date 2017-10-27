package com.alibaba.sdk.android.mns.common;

/**
 * Created by pan.zengp on 2016/7/27.
 */
import com.alibaba.sdk.android.common.utils.HttpHeaders;
public interface MNSHeaders extends HttpHeaders{
    String MNS_PREFIX = "x-mns-";
    String MNS_HEADER_REQUEST_ID = "x-mns-request-id";
    String MNS_SECURITY_TOKEN = "x-mns-security-token";
    String MNS_META_OVERRIDE = "metaoverride";
    String MNS_QUEUE_PREFIX = "x-mns-prefix";
    String MNS_MARKER = "x-mns-marker";
    String MNS_RET_NUMBERS = "x-mns-ret-number";
    String MNS_WITH_META = "x-mns-with-meta";
    String MNS_PEEK_ONLY = "peekonly";
}
