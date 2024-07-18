package com.pw.gwhservice1.gwhposting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pw.gwhservice1.model.Entity;
import com.pw.gwhservice1.model.SSBOfficeCdMap;

public class PostingGenerationService implements BuzService {

    private static final String AUDIT_STATUS = "auditStatus";
    private static final String GMCONVERTEDFLG = "gmConvertedFlg";
    private static final String DDACONVERTEDFLG = "ddaConvertedFlg";
    private static final String SWIFT_MSG_TYPE_TXT = "SwiftType";
    private static final String SETTLE_CCY_CD = "CurrencyCodeBase";
    private static final String GM_IBS_INSTALLATION = "GmIbsInstallation";
    private static final String GM_IBS_SITE_BRANCH_TXT = "GmIbsBranchTxt";
    private static final String GM_IBS_SITE_TXT = "GmIbsSiteTxt";
    private static final String IBS_INSTALLATION = "IbsInstallation";
    private static final String IBS_SITE_BRANCH_TXT = "IbsBranchTxt";
    private static final String IBS_SITE_TXT = "IbsSiteTxt";
    private static final String DDA_IBS_INSTALLATION = "DdaIbsInstallation";
    private static final String DDA_IBS_SITE_BRANCH_TXT = "DdaIbsBranchTxt";
    private static final String DDA_IBS_SITE_TXT = "DdaIbsSiteTxt";
    private static final String GM_ACCOUNT_ID = "GmAccountId";
    private static final String GM_ACCOUNT_TYPE = "GmAccountType";
    private static final String DDA_ACCOUNT_ID = "DdaAccountId";
    private static final String DDA_ACCOUNT_TYPE = "DdaAccountType";
    private static final String TYPE = "TYPE";
    private static final String INBOUND_MESSAGE_ID = "InboundMessageId";
    private static final String FROM_WUI_REPROCESS = "FromWuiReprocess";

    private static final String MESSAGE_TYPE_200 = "200";

    @Override
    public Entity process(Entity msg) throws RuntimeException {

        msg.set(AUDIT_STATUS, null);
        generateOutboundMessage(msg);
        return msg;
    }

    private void generateOutboundMessage(Entity msg) throws RuntimeException {

        List<Map<String, String>> parametersMaps = new ArrayList<>();

        String gmConvertedFlg = msg.get(GMCONVERTEDFLG);
        String ddaConvertedFlg = msg.get(DDACONVERTEDFLG);
        String swiftMsgType = msg.get(SWIFT_MSG_TYPE_TXT);
        //String ibsQName = commonService.getIbsQName();
        SSBOfficeCdMap gmSsbOfficeCdMap = (SSBOfficeCdMap) msg.get("gmSsbOfficeCdMap");
        SSBOfficeCdMap ddaSsbOfficeCdMap = (SSBOfficeCdMap) msg.get("ddaSsbOfficeCdMap");
        String currency = msg.get(SETTLE_CCY_CD);
        String gmIbsInstall = msg.get(GM_IBS_INSTALLATION);
        String gmIbsBranch = msg.get(GM_IBS_SITE_BRANCH_TXT);
        String ddaIbsInstall = msg.get(DDA_IBS_INSTALLATION);
        String ddaIbsBranch = msg.get(DDA_IBS_SITE_BRANCH_TXT);
        String gmAccount = msg.get(GM_ACCOUNT_ID);
        String ddaAccount = msg.get(DDA_ACCOUNT_ID);
        String rawMessage = StringUtils.EMPTY;
        Boolean isOverride = msg.get("isOverride");
        String offsetMsgType = MESSAGE_TYPE_200;
        String originalManageType = StringUtils.EMPTY;
        String compositeKey = StringUtils.EMPTY;
        Long inboundMsgId = msg.get(INBOUND_MESSAGE_ID);
        String type = msg.get(TYPE);
        Boolean isSingle = msg.get("isSingle");
        Map<String, String> parametersMap = new HashMap<>();

        String isFromWui = "false";
        isFromWui = msg.get(FROM_WUI_REPROCESS);
        String ssbTid = "";
        String cltCpRef = "";
    }

}
