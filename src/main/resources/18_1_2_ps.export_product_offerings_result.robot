*** Settings ***
Resource          ../../../../resource/common.robot
Resource          ../Ccm_export_keywords.robot

Suite Setup         Prepare Suite
Test Setup          Prepare Test
Suite Teardown      Teardown Suite
*** Variables ***


*** Test Cases ***
C4671260: ps.export_product_offerings_result (1 po, 1 spec, 1 service with thresholds)
    [Documentation]     ps.export_product_offerings_result (1 po, 1 spec, 1 service with thresholds)
    [Tags]      POSITIVE
    ${testId}=          Set Variable    C4671260

    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Set Variable            137
    ${typeId}=                  Set Variable            138
    ${bisType}=                 Set Variable            PACK
    ${serviceId}=               Set Variable            153
    ${switchId}=                Set Variable            3
    ${whenActivate}=            Set Variable            Y
    ${whenDeactivate}=          Set Variable            N
    ${manualActiv}=             Set Variable            N
    ${manualDeactiv}=           Set Variable            N
    ${uncondChangeStatus}=      Set Variable            N
    ${undepOrder}=              Set Variable            4
    ${orderPriority}=           Set Variable            3
    ${isBase}=                  Set Variable            true
    ${isContent}=               Set Variable            false
    ${underRTControl}=          Set Variable            true
    ${fileName}=                Catenate    SEPARATOR=     ${poId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    
    ${warning}=                 Set Variable            150
    ${break}=                   Set Variable            200
    ${activate}=                Set Variable            250

    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId}      ${switchId}    ${whenActivate}
    ...             ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${uncondChangeStatus}   ${undepOrder}
    ...             ${orderPriority}    ${isBase}   ${isContent}    ${underRTControl}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId}  ${SpecId}      ${serviceId}   ${warning}  ${break}    ${activate}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   2        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}
    Check Service Parameters In Catalog     ${serviceId}    ${poId}    ${isBase}     ${underRTControl}   ${isContent}
    Check ServSwitch Parameters In Catalog  ${serviceId}    ${poId}    ${switchId}  ${undepOrder}   ${uncondChangeStatus}
    ...                     ${whenActivate}     ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${orderPriority}
    Check Threshold Parameters In Catalog        ${serviceId}   ${poId}      ${warning}      ${break}    ${activate}


    Check If Error Log Is Empty     ${date}


C4408823: ps.export_product_offerings_result, several json in archive
    [Documentation]     ps.export_product_offerings_result, several json in archive
    [Tags]      POSITIVE
    ${testId}=          Set Variable    C4408823

    #ProductOffering 1
    ${date1}=                    Get Current Time
    ${poId1}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId1}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId1}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType1}=                 Set Variable            PACK
    ${serviceId1}=               Convert Test Id To Service Id      ${testId}    4
    ${switchId1}=                Convert Test Id To Service Id      ${testId}    5
    ${whenActivate1}=            Set Variable            Y
    ${whenDeactivate1}=          Set Variable            N
    ${manualActiv1}=             Set Variable            Y
    ${manualDeactiv1}=           Set Variable            N
    ${uncondChangeStatus1}=      Set Variable            Y
    ${undepOrder1}=              Set Variable            1
    ${orderPriority1}=           Set Variable            2
    ${isBase1}=                  Set Variable            true
    ${isContent1}=               Set Variable            false
    ${underRTControl1}=          Set Variable            true
    ${warning1}=                 Set Variable            150
    ${break1}=                   Set Variable            200
    ${activate1}=                Set Variable            250

    ${res}=         Add Product Offering File       ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId1}   ${date1}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId1}     ${SpecId1}   ${typeId1}    ${bisType1}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId1}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId1}  ${SpecId1}      ${serviceId1}      ${switchId1}    ${whenActivate1}
    ...             ${whenDeactivate1}   ${manualActiv1}  ${manualDeactiv1}    ${uncondChangeStatus1}   ${undepOrder1}
    ...             ${orderPriority1}    ${isBase1}   ${isContent1}    ${underRTControl1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId1}  ${SpecId1}      ${serviceId1}   ${warning1}  ${break1}    ${activate1}
    Should Be Equal As Integers    ${res}     1

    #ProductOffering 2
    ${date2}=                    Get Current Time
    ${poId2}=                    Convert Test Id To Service Id      ${testId}    21
    ${SpecId2}=                  Convert Test Id To Service Id      ${testId}    22
    ${typeId2}=                  Convert Test Id To Service Id      ${testId}    23
    ${bisType2}=                 Set Variable            RATE_PLAN
    ${serviceId2}=               Convert Test Id To Service Id      ${testId}    24
    ${switchId2}=                Convert Test Id To Service Id      ${testId}    25
    ${whenActivate2}=            Set Variable            N
    ${whenDeactivate2}=          Set Variable            Y
    ${manualActiv2}=             Set Variable            N
    ${manualDeactiv2}=           Set Variable            Y
    ${uncondChangeStatus2}=      Set Variable            N
    ${undepOrder2}=              Set Variable            3
    ${orderPriority2}=           Set Variable            4
    ${isBase2}=                  Set Variable            false
    ${isContent2}=               Set Variable            true
    ${underRTControl2}=          Set Variable            false
    ${warning2}=                 Set Variable            300
    ${break2}=                   Set Variable            350
    ${activate2}=                Set Variable            400

    ${res}=         Add Product Offering File       ${poId2}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId2}   ${date2}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId2}     ${SpecId2}   ${typeId2}    ${bisType2}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId2}  ${SpecId2}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId2}  ${SpecId2}      ${serviceId2}      ${switchId2}    ${whenActivate2}
    ...             ${whenDeactivate2}   ${manualActiv2}  ${manualDeactiv2}    ${uncondChangeStatus2}   ${undepOrder2}
    ...             ${orderPriority2}    ${isBase2}   ${isContent2}    ${underRTControl2}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId2}  ${SpecId2}      ${serviceId2}   ${warning2}  ${break2}    ${activate2}
    Should Be Equal As Integers    ${res}     1

    #ProductOffering 3
    ${date3}=                    Get Current Time
    ${poId3}=                    Convert Test Id To Service Id      ${testId}    31
    ${SpecId3}=                  Convert Test Id To Service Id      ${testId}    32
    ${typeId3}=                  Convert Test Id To Service Id      ${testId}    33
    ${bisType3}=                 Set Variable            SERVICE
    ${serviceId3}=               Convert Test Id To Service Id      ${testId}    34
    ${switchId3}=                Convert Test Id To Service Id      ${testId}    35
    ${whenActivate3}=            Set Variable            N
    ${whenDeactivate3}=          Set Variable            N
    ${manualActiv3}=             Set Variable            N
    ${manualDeactiv3}=           Set Variable            Y
    ${uncondChangeStatus3}=      Set Variable            Y
    ${undepOrder3}=              Set Variable            3
    ${orderPriority3}=           Set Variable            4
    ${isBase3}=                  Set Variable            true
    ${isContent3}=               Set Variable            true
    ${underRTControl3}=          Set Variable            false
    ${warning3}=                 Set Variable            400
    ${break3}=                   Set Variable            -350
    ${activate3}=                Set Variable            450

    ${res}=         Add Product Offering File       ${poId3}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId3}   ${date3}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId3}     ${SpecId3}   ${typeId3}    ${bisType3}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId3}  ${SpecId3}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId3}  ${SpecId3}      ${serviceId3}      ${switchId3}    ${whenActivate3}
    ...             ${whenDeactivate3}   ${manualActiv3}  ${manualDeactiv3}    ${uncondChangeStatus3}   ${undepOrder3}
    ...             ${orderPriority3}    ${isBase3}   ${isContent3}    ${underRTControl3}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId3}  ${SpecId3}      ${serviceId3}   ${warning3}  ${break3}    ${activate3}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date1}    ${fileName}    ${path}     ${None}

    Check ProductOffering In Catalog        ${poId1}   ${typeId1}   ${bisType1}
    Check Service Parameters In Catalog     ${serviceId1}    ${poId1}    ${isBase1}     ${underRTControl1}   ${isContent1}
    Check ServSwitch Parameters In Catalog  ${serviceId1}    ${poId1}    ${switchId1}  ${undepOrder1}   ${uncondChangeStatus1}
    ...                     ${whenActivate1}     ${whenDeactivate1}   ${manualActiv1}  ${manualDeactiv1}    ${orderPriority1}
    Check Threshold Parameters In Catalog        ${serviceId1}  ${poId1}       ${warning1}      ${break1}    ${activate1}

    Check ProductOffering In Catalog        ${poId2}   ${typeId2}   ${bisType2}
    Check Service Parameters In Catalog     ${serviceId2}    ${poId2}    ${isBase2}     ${underRTControl2}   ${isContent2}
    Check ServSwitch Parameters In Catalog  ${serviceId2}    ${poId2}    ${switchId2}  ${undepOrder2}   ${uncondChangeStatus2}
    ...                     ${whenActivate2}     ${whenDeactivate2}   ${manualActiv2}  ${manualDeactiv2}    ${orderPriority2}
    Check Threshold Parameters In Catalog        ${serviceId2}  ${poId2}       ${warning2}      ${break2}    ${activate2}

    Check ProductOffering In Catalog        ${poId3}   ${typeId3}   ${bisType3}
    Check Service Parameters In Catalog     ${serviceId3}    ${poId3}    ${isBase3}     ${underRTControl3}   ${isContent3}
    Check ServSwitch Parameters In Catalog  ${serviceId3}    ${poId3}    ${switchId3}  ${undepOrder3}   ${uncondChangeStatus3}
    ...                     ${whenActivate3}     ${whenDeactivate3}   ${manualActiv3}  ${manualDeactiv3}    ${orderPriority3}
    Check Threshold Parameters In Catalog        ${serviceId3}  ${poId3}       ${warning3}      ${break3}    ${activate3}

    Check If Error Log Is Empty     ${date1}


C4439875: ps.export_product_offerings_result, 1 productOfferings without services
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings without services
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4439875

    #ProductOffering 1
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType}=                 Set Variable            PACK

    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}

    Check If Error Log Is Empty     ${date}


C4671238: ps.export_product_offerings_result, 1 productOfferings with 1 service without thresholds
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings with 1 service without thresholds
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671238

    #ProductOffering
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType}=                 Set Variable            PACK
    ${serviceId}=               Convert Test Id To Service Id      ${testId}    4
    ${switchId}=                Convert Test Id To Service Id      ${testId}    5
    ${whenActivate}=            Set Variable            Y
    ${whenDeactivate}=          Set Variable            N
    ${manualActiv}=             Set Variable            Y
    ${manualDeactiv}=           Set Variable            N
    ${uncondChangeStatus}=      Set Variable            Y
    ${undepOrder}=              Set Variable            1
    ${orderPriority}=           Set Variable            2
    ${isBase}=                  Set Variable            true
    ${isContent}=               Set Variable            false
    ${underRTControl}=          Set Variable            true

    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId}      ${switchId}    ${whenActivate}
    ...             ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${uncondChangeStatus}   ${undepOrder}
    ...             ${orderPriority}    ${isBase}   ${isContent}    ${underRTControl}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable                /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}
    Check Service Parameters In Catalog     ${serviceId}    ${poId}    ${isBase}     ${underRTControl}   ${isContent}
    Check ServSwitch Parameters In Catalog  ${serviceId}    ${poId}    ${switchId}  ${undepOrder}   ${uncondChangeStatus}
    ...                     ${whenActivate}     ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${orderPriority}

    Check If Error Log Is Empty     ${date}


C4671239: ps.export_product_offerings_result, 1 productOfferings with 1 service with 1 thresholds
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings with 1 service with 1 thresholds
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671239

    #ProductOffering
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType}=                 Set Variable            PACK
    ${serviceId}=               Convert Test Id To Service Id      ${testId}    4
    ${switchId}=                Convert Test Id To Service Id      ${testId}    5
    ${whenActivate}=            Set Variable            Y
    ${whenDeactivate}=          Set Variable            N
    ${manualActiv}=             Set Variable            Y
    ${manualDeactiv}=           Set Variable            N
    ${uncondChangeStatus}=      Set Variable            Y
    ${undepOrder}=              Set Variable            1
    ${orderPriority}=           Set Variable            2
    ${isBase}=                  Set Variable            true
    ${isContent}=               Set Variable            false
    ${underRTControl}=          Set Variable            true
    ${warning}=                 Set Variable            1000


    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId}      ${switchId}    ${whenActivate}
    ...             ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${uncondChangeStatus}   ${undepOrder}
    ...             ${orderPriority}    ${isBase}   ${isContent}    ${underRTControl}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId}  ${SpecId}      ${serviceId}   ${warning}   ${None}   ${None}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}
    Check Service Parameters In Catalog     ${serviceId}    ${poId}    ${isBase}     ${underRTControl}   ${isContent}
    Check ServSwitch Parameters In Catalog  ${serviceId}    ${poId}    ${switchId}  ${undepOrder}   ${uncondChangeStatus}
    ...                     ${whenActivate}     ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${orderPriority}
    Check Threshold Parameters In Catalog        ${serviceId}   ${poId}       ${warning}      ${None}    ${None}

    Check If Error Log Is Empty     ${date}


C4671244: ps.export_product_offerings_result, 1 productOfferings with 1 service with all thresholds
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings with 1 service with all thresholds
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671244

    #ProductOffering
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType}=                 Set Variable            PACK
    ${serviceId}=               Convert Test Id To Service Id      ${testId}    4
    ${switchId}=                Convert Test Id To Service Id      ${testId}    5
    ${whenActivate}=            Set Variable            Y
    ${whenDeactivate}=          Set Variable            N
    ${manualActiv}=             Set Variable            Y
    ${manualDeactiv}=           Set Variable            N
    ${uncondChangeStatus}=      Set Variable            Y
    ${undepOrder}=              Set Variable            1
    ${orderPriority}=           Set Variable            2
    ${isBase}=                  Set Variable            true
    ${isContent}=               Set Variable            false
    ${underRTControl}=          Set Variable            true
    ${warning}=                 Set Variable            1400
    ${break}=                   Set Variable            -1350
    ${activate}=                Set Variable            4250


    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId}      ${switchId}    ${whenActivate}
    ...             ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${uncondChangeStatus}   ${undepOrder}
    ...             ${orderPriority}    ${isBase}   ${isContent}    ${underRTControl}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId}  ${SpecId}      ${serviceId}   ${warning}   ${break}   ${activate}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}
    Check Service Parameters In Catalog     ${serviceId}    ${poId}    ${isBase}     ${underRTControl}   ${isContent}
    Check ServSwitch Parameters In Catalog  ${serviceId}    ${poId}    ${switchId}  ${undepOrder}   ${uncondChangeStatus}
    ...                     ${whenActivate}     ${whenDeactivate}   ${manualActiv}  ${manualDeactiv}    ${orderPriority}
    Check Threshold Parameters In Catalog        ${serviceId}   ${poId}       ${warning}      ${break}    ${activate}

    Check If Error Log Is Empty     ${date}


C4671245: ps.export_product_offerings_result, 1 productOfferings with n services
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings with n services
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671245

    #ProductOffering
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType}=                 Set Variable            PACK
    ${serviceId1}=               Convert Test Id To Service Id      ${testId}    4
    ${switchId1}=                Convert Test Id To Service Id      ${testId}    5
    ${whenActivate1}=            Set Variable            Y
    ${whenDeactivate1}=          Set Variable            N
    ${manualActiv1}=             Set Variable            Y
    ${manualDeactiv1}=           Set Variable            N
    ${uncondChangeStatus1}=      Set Variable            Y
    ${undepOrder1}=              Set Variable            6
    ${orderPriority1}=           Set Variable            7
    ${isBase1}=                  Set Variable            true
    ${isContent1}=               Set Variable            true
    ${underRTControl1}=          Set Variable            true
    ${warning1}=                 Set Variable            1400
    ${break1}=                   Set Variable            -1350
    ${activate1}=                Set Variable            4250

    ${serviceId2}=               Convert Test Id To Service Id      ${testId}    6
    ${switchId2}=                Convert Test Id To Service Id      ${testId}    7
    ${whenActivate2}=            Set Variable            Y
    ${whenDeactivate2}=          Set Variable            Y
    ${manualActiv2}=             Set Variable            Y
    ${manualDeactiv2}=           Set Variable            Y
    ${uncondChangeStatus2}=      Set Variable            Y
    ${undepOrder2}=              Set Variable            3
    ${orderPriority2}=           Set Variable            4
    ${isBase2}=                  Set Variable            false
    ${isContent2}=               Set Variable            false
    ${underRTControl2}=          Set Variable            true
    ${warning2}=                 Set Variable            -13050
    ${break2}=                   Set Variable            0
    ${activate2}=                Set Variable            42250

    ${serviceId3}=               Convert Test Id To Service Id      ${testId}    8
    ${switchId3}=                Convert Test Id To Service Id      ${testId}    9
    ${whenActivate3}=            Set Variable            N
    ${whenDeactivate3}=          Set Variable            N
    ${manualActiv3}=             Set Variable            N
    ${manualDeactiv3}=           Set Variable            N
    ${uncondChangeStatus3}=      Set Variable            N
    ${undepOrder3}=              Set Variable            9
    ${orderPriority3}=           Set Variable            10
    ${isBase3}=                  Set Variable            true
    ${isContent3}=               Set Variable            false
    ${underRTControl3}=          Set Variable            false



    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId}  ${SpecId}     ${None}
    Should Be Equal As Integers    ${res}     1
    # Service 1
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId1}      ${switchId1}    ${whenActivate1}
    ...             ${whenDeactivate1}   ${manualActiv1}  ${manualDeactiv1}    ${uncondChangeStatus1}   ${undepOrder1}
    ...             ${orderPriority1}    ${isBase1}   ${isContent1}    ${underRTControl1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId}  ${SpecId}      ${serviceId1}   ${warning1}   ${break1}   ${activate1}
    Should Be Equal As Integers    ${res}     1
    # Service 2
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId2}      ${switchId2}    ${whenActivate2}
    ...             ${whenDeactivate2}   ${manualActiv2}  ${manualDeactiv2}    ${uncondChangeStatus2}   ${undepOrder2}
    ...             ${orderPriority2}    ${isBase2}   ${isContent2}    ${underRTControl2}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId}  ${SpecId}      ${serviceId2}   ${warning2}   ${break2}   ${activate2}
    Should Be Equal As Integers    ${res}     1
    # Service 3
    ${res}=         Add Customer Facing Service   ${poId}  ${SpecId}      ${serviceId3}      ${switchId3}    ${whenActivate3}
    ...             ${whenDeactivate3}   ${manualActiv3}  ${manualDeactiv3}    ${uncondChangeStatus3}   ${undepOrder3}
    ...             ${orderPriority3}    ${isBase3}   ${isContent3}    ${underRTControl3}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}

    Check Service Parameters In Catalog     ${serviceId1}    ${poId}    ${isBase1}     ${underRTControl1}   ${isContent1}
    Check ServSwitch Parameters In Catalog  ${serviceId1}    ${poId}    ${switchId1}  ${undepOrder1}   ${uncondChangeStatus1}
    ...                     ${whenActivate1}     ${whenDeactivate1}   ${manualActiv1}  ${manualDeactiv1}    ${orderPriority1}
    Check Threshold Parameters In Catalog        ${serviceId1}  ${poId}       ${warning1}      ${break1}    ${activate1}

    Check Service Parameters In Catalog     ${serviceId2}    ${poId}    ${isBase2}     ${underRTControl2}   ${isContent2}
    Check ServSwitch Parameters In Catalog  ${serviceId2}    ${poId}    ${switchId2}  ${undepOrder2}   ${uncondChangeStatus2}
    ...                     ${whenActivate2}     ${whenDeactivate2}   ${manualActiv2}  ${manualDeactiv2}    ${orderPriority2}
    Check Threshold Parameters In Catalog        ${serviceId2}  ${poId}       ${warning2}      ${break2}    ${activate2}

    Check Service Parameters In Catalog     ${serviceId3}    ${poId}    ${isBase3}     ${underRTControl3}   ${isContent3}
    Check ServSwitch Parameters In Catalog  ${serviceId3}    ${poId}    ${switchId3}  ${undepOrder3}   ${uncondChangeStatus3}
    ...                     ${whenActivate3}     ${whenDeactivate3}   ${manualActiv3}  ${manualDeactiv3}    ${orderPriority3}

    Check If Error Log Is Empty     ${date}


C4671246: ps.export_product_offerings_result, 1 productOfferings with internal productOfferings
    [Documentation]     ps.export_product_offerings_result, 1 productOfferings with internal productOfferings
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671246

    #ProductOffering 1
    ${date}=                    Get Current Time
    ${poId1}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId1}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId1}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType1}=                 Set Variable            PACK

    ${poId2}=                    Convert Test Id To Service Id      ${testId}    4
    ${SpecId2}=                  Convert Test Id To Service Id      ${testId}    5
    ${typeId2}=                  Convert Test Id To Service Id      ${testId}    6
    ${bisType2}=                 Set Variable            RATE_PLAN

    ${poId3}=                    Convert Test Id To Service Id      ${testId}    7
    ${SpecId3}=                  Convert Test Id To Service Id      ${testId}    8
    ${typeId3}=                  Convert Test Id To Service Id      ${testId}    9
    ${bisType3}=                 Set Variable            SERVICE

    ${path}=                    Set Variable            /${testId}/

    ${res}=         Add Product Offering File       ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId1}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId1}     ${SpecId1}   ${typeId1}    ${bisType1}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId1}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Product Offering    ${poId2}     ${SpecId2}   ${typeId2}    ${bisType2}      ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId2}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Product Offering    ${poId3}     ${SpecId3}   ${typeId3}    ${bisType3}      ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId3}     ${None}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId1}   ${typeId1}   ${bisType1}
    Check ProductOffering In Catalog        ${poId2}   ${typeId2}   ${bisType2}
    Check ProductOffering In Catalog        ${poId3}   ${typeId3}   ${bisType3}

    Check If Error Log Is Empty     ${date}


C4671251: ps.export_product_offerings_result, three levels of productOfferings
    [Documentation]     ps.export_product_offerings_result, three levels of productOfferings
    [Tags]              POSITIVE
    ${testId}=          Set Variable    C4671251

    #ProductOffering 1
    ${date}=                    Get Current Time
    ${poId1}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId1}=                  Convert Test Id To Service Id      ${testId}    2
    ${typeId1}=                  Convert Test Id To Service Id      ${testId}    3
    ${bisType1}=                 Set Variable            PACK

    ${poId2}=                    Convert Test Id To Service Id      ${testId}    4
    ${SpecId2}=                  Convert Test Id To Service Id      ${testId}    5
    ${typeId2}=                  Convert Test Id To Service Id      ${testId}    6
    ${bisType2}=                 Set Variable            RATE_PLAN

    ${poId3}=                    Convert Test Id To Service Id      ${testId}    7
    ${SpecId3}=                  Convert Test Id To Service Id      ${testId}    8
    ${typeId3}=                  Convert Test Id To Service Id      ${testId}    9
    ${bisType3}=                 Set Variable            SERVICE

    ${poId4}=                    Convert Test Id To Service Id      ${testId}    10
    ${SpecId4}=                  Convert Test Id To Service Id      ${testId}    11
    ${typeId4}=                  Convert Test Id To Service Id      ${testId}    12
    ${bisType4}=                 Set Variable            SERVICE

    ${serviceId4}=               Convert Test Id To Service Id      ${testId}    13
    ${switchId4}=                Convert Test Id To Service Id      ${testId}    14
    ${whenActivate4}=            Set Variable            N
    ${whenDeactivate4}=          Set Variable            Y
    ${manualActiv4}=             Set Variable            N
    ${manualDeactiv4}=           Set Variable            N
    ${uncondChangeStatus4}=      Set Variable            N
    ${undepOrder4}=              Set Variable            12
    ${orderPriority4}=           Set Variable            10
    ${isBase4}=                  Set Variable            true
    ${isContent4}=               Set Variable            false
    ${underRTControl4}=          Set Variable            false

    ${warning4}=                 Set Variable            1400
    ${break4}=                   Set Variable            -1350
    ${activate4}=                Set Variable            4250

    ${path}=                    Set Variable            /${testId}/

    ${res}=         Add Product Offering File       ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId1}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId1}     ${SpecId1}   ${typeId1}    ${bisType1}      ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId1}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Product Offering    ${poId2}     ${SpecId2}   ${typeId2}    ${bisType2}      ${poId1}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId2}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Product Offering    ${poId3}     ${SpecId3}   ${typeId3}    ${bisType3}      ${poId2}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId3}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Product Offering    ${poId4}     ${SpecId4}   ${typeId4}    ${bisType4}      ${poId2}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Specification   ${poId1}  ${SpecId4}     ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Add Customer Facing Service   ${poId1}  ${SpecId4}      ${serviceId4}      ${switchId4}    ${whenActivate4}
    ...             ${whenDeactivate4}   ${manualActiv4}  ${manualDeactiv4}    ${uncondChangeStatus4}   ${undepOrder4}
    ...             ${orderPriority4}    ${isBase4}   ${isContent4}    ${underRTControl4}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Threshold To CFService   ${poId4}  ${SpecId4}      ${serviceId4}   ${warning4}   ${break4}   ${activate4}
    Should Be Equal As Integers    ${res}     1

    #Create archive
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId1}   ${typeId1}   ${bisType1}
    Check ProductOffering In Catalog        ${poId2}   ${typeId2}   ${bisType2}
    Check ProductOffering In Catalog        ${poId3}   ${typeId3}   ${bisType3}
    Check ProductOffering In Catalog        ${poId4}   ${typeId4}   ${bisType4}

    Check Service Parameters In Catalog     ${serviceId4}    ${poId4}    ${isBase4}     ${underRTControl4}   ${isContent4}
    Check ServSwitch Parameters In Catalog  ${serviceId4}    ${poId4}    ${switchId4}  ${undepOrder4}   ${uncondChangeStatus4}
    ...                     ${whenActivate4}     ${whenDeactivate4}   ${manualActiv4}  ${manualDeactiv4}    ${orderPriority4}
    Check Threshold Parameters In Catalog        ${serviceId4}  ${poId4}       ${warning4}      ${break4}    ${activate4}

    Check If Error Log Is Empty     ${date}


C4671261: ps.export_product_offerings_result, archive not exists
    [Documentation]     ps.export_product_offerings_result, archive not exists
    ${testId}=          Set Variable    C4671261

    ${date}=                    Get Current Time
    ${fileName}=                Catenate    SEPARATOR=     ${testId}   .tar.gz
    ${path}=                    Set Variable            /
    ${logString}=               Set Variable        Error during reading file ${fileName}

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check If Error Log Is Not Empty     ${date}
    Check Ufm Logs      ${logString}       ${date}


C4671262: ps.export_product_offerings_result, filename not exists in message
    [Documentation]     ps.export_product_offerings_result, filename not exists
    ${testId}=          Set Variable    C4671262

    #ProductOffering 1
    ${date}=                    Get Current Time
    ${logString}=               Set Variable        Error during reading file
    ${path}=                    Set Variable        /
    
    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${None}     ${path}     ${None}


    Check If Error Log Is Not Empty     ${date}
    Check Ufm Logs      ${logString}       ${date}


C4671263: ps.export_product_offerings_result, spec not exists in json
    [Documentation]     ps.export_product_offerings_result, spec not exists in json
    ${testId}=          Set Variable    C4671263
    ${logString}=       Set Variable     ???

    #ProductOffering 1
    ${date}=                    Get Current Time
    ${poId}=                    Convert Test Id To Service Id      ${testId}
    ${SpecId}=                  Set Variable            180
    ${typeId}=                  Set Variable            1238
    ${bisType}=                 Set Variable            PACK
    ${fileName}=                Catenate    SEPARATOR=     ${poId}   .tar.gz
    ${path}=                    Set Variable            /${testId}/
    ${res}=         Add Product Offering File       ${poId}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Exported Date Time    ${poId}   ${date}     ${None}
    Should Be Equal As Integers    ${res}     1
    ${res}=         Add Product Offering    ${poId}     ${SpecId}   ${typeId}    ${bisType}      ${None}
    Should Be Equal As Integers    ${res}     1

    ${res}=         Create Product Offerings Archive Using Path    ${pathToFolder}${path}   ${fileName}
    Should Be Equal As Integers    ${res}     1

    Send Product Offerings Export Result Message    ${testId}   1        ${date}    ${fileName}     ${path}     ${None}

    Check ProductOffering In Catalog        ${poId}   ${typeId}   ${bisType}

    Check If Error Log Is Empty     ${date}