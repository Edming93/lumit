SET FOREIGN_KEY_CHECKS = 0;

drop table if exists TB_BOARD cascade;
drop table if exists TB_CARTS cascade;
drop table if exists TB_CATEGORY cascade;
drop table if exists TB_CATEGORY_MAP cascade;
drop table if exists TB_COLOR cascade;
drop table if exists TB_COUPON cascade;
drop table if exists TB_COUPON_USE_HIS cascade;
drop table if exists TB_DELIVERY cascade;
drop table if exists TB_FILE cascade;
drop table if exists TB_INVOICE cascade;
drop table if exists TB_LIKE cascade;
drop table if exists TB_LOGIN cascade;
drop table if exists TB_LOGIN_HIS cascade;
drop table if exists TB_MENU cascade;
drop table if exists TB_ORDER cascade;
drop table if exists TB_ORDER_DETAIL cascade;
drop table if exists TB_PAY_BANK cascade;
drop table if exists TB_PAY_CARD cascade;
drop table if exists TB_PAYMENT_PLAN cascade;
drop table if exists TB_PRODUCT cascade;
drop table if exists TB_PRODUCT_OPTION cascade;
drop table if exists TB_PRODUCT_QNA cascade;
drop table if exists TB_REVIEW cascade;
drop table if exists TB_ROLE cascade;
drop table if exists TB_ROLE_MENU cascade;
drop table if exists TB_TAG cascade;
drop table if exists TB_TAG_MAP cascade;
drop table if exists TB_ADDRESS cascade;
SET FOREIGN_KEY_CHECKS = 1;

create table if not exists TB_BOARD
(
    BOARD_ID   int AUTO_INCREMENT not null comment 'AUTO_INCREMENT'
        primary key,
    MENU_CD    varchar(4)         not null comment '메뉴 코드',
    MENU_DV_CD varchar(4)         null comment '메뉴구분코드',
    TITLE      varchar(255)       not null,
    CONTENT    text               null,
    PASSWORD   varchar(50)        null,
    TOP_FIX    varchar(2)         null,
    USE_YN     varchar(2)         not null comment '사용여부',
    DEL_YN     varchar(2)         not null comment '삭제여부',
    RPLY_YN    varchar(2)         null comment '회신여부',
    FILE_YN    varchar(2)         not null,
    VIEW_COUNT int DEFAULT 0      null,
    REG_ID     varchar(50)        not null,
    REG_DT     timestamp          not null,
    MOD_ID     varchar(50)        not null,
    MOD_DT     timestamp          not null
);

create table if not exists TB_CARTS
(
    CART_ID       int         not null comment 'AUTO_INCREMENT'
        primary key,
    USER_ID       varchar(50) not null,
    PRODUCT_ID    int         not null comment 'AUTO_INCREMENT',
    OPTION_ID     int         not null comment 'AUTO_INCREMENT',
    CART_QUANTITY int         not null,
    CART_STATUS   varchar(2)  not null,
    REG_ID        varchar(50) null,
    REG_DT        timestamp   null,
    MOD_ID        varchar(50) null,
    MOD_DT        timestamp   null
);


create table if not exists TB_CATEGORY
(
    CATEGORY_ID   bigint      not null
        primary key,
    CATEGORY_NAME varchar(50) not null,
    USE_YN        varchar(2)  not null,
    DEPTH         int         not null comment '{0: 최상위, 순차로 하위}'
);

create table if not exists TB_CATEGORY_MAP
(
    CATEGORY_ID bigint not null,
    PRODUCT_ID  int    not null comment 'AUTO_INCREMENT'
);

create table if not exists TB_COLOR
(
    COLOR_ID   int         not null comment 'AUTO_INCREMENT'
        primary key,
    PRODUCT_ID int         not null comment 'AUTO_INCREMENT',
    COLOR_NAME varchar(20) not null
);

create table if not exists TB_COUPON
(
    COUPON_ID               bigint         not null comment 'AUTO_INCREMENT'
        primary key,
    USER_ID                 varchar(50)    not null,
    COUPON_CODE             varchar(255)   not null,
    COUPON_NAME             varchar(50)    not null,
    COUPON_TYPE             tinyint        not null comment '1: 주문금액에 대해 퍼센트 할인, 2: 특정 금액 할인(ex: 1000, 10000), 3: 신규회원 쿠폰',
    DISCOUNT_AMOUNT         decimal(10, 2) not null comment '쿠폰 유형이 1일 경우 소수점으로 할인율 기입(예: 0.1은 10프로 할인),  2일 경우 액수 기입, 3일 경우 규정에 따라 기입',
    MIN_ORDER_AMOUNT        decimal(10, 2) null comment '쿠폰 사용을 위한 최소 주문 금액',
    MAX_DISCOUNT_AMOUNT     decimal(10, 2) null,
    VALID_FROM              datetime       not null,
    VALID_TO                datetime       not null,
    COUPON_DESCRIPTION      varchar(255)   null,
    RESTRICTED_USE          tinyint        not null comment '0: 제한 없음, 1: 특정 제품 또는 카테고리에만 사용 가능',
    RESTRICTED_PRODUCT_IDS  varchar(255)   null comment '쿠폰이 적용되는 제품의 ID 목록을 쉼표로 구분',
    RESTRICTED_CATEGORY_IDS varchar(255)   null comment '쿠폰이 적용되는 카테고리 ID 목록을 쉼표로 구분',
    REG_DT                  timestamp      not null,
    REG_ID                  varchar(50)    not null
);

create table if not exists TB_COUPON_USE_HIS
(
    USER_ID         varchar(50)    not null
        primary key,
    COUPON_ID       bigint         not null comment 'AUTO_INCREMENT',
    COUPON_NAME     varchar(50)    not null,
    USE_STATUS_CD   varchar(2)     not null comment '{1: 사용 안함, 2: 사용함, 3: 만료됨}',
    DISCOUNT_AMT    decimal(10, 2) null,
    VALID_FROM      datetime       not null,
    VALID_TO        datetime       not null,
    DISCOUNT_AMOUNT decimal(10, 2) null,
    REG_ID          varchar(50)    not null,
    REG_DT          timestamp      not null
);

create table if not exists TB_DELIVERY
(
    DELIVERY_ID      bigint         not null comment 'AUTO_INCREMENT'
        primary key,
    INVOICE_ID       int            not null comment 'AUTO_INCREMENT',
    DELIVERY_METHOD  varchar(255)   not null,
    DELIVERY_ADDRESS varchar(255)   not null,
    DELIVERY_ZIPCODE varchar(255)   not null,
    DELIVERY_COUNTRY varchar(255)   not null,
    DELIVERY_STATUS  tinyint        not null,
    EXPECTED_DATE    datetime       not null,
    TRACKING_NUMBER  varchar(255)   not null,
    DELIVERY_COMPANY varchar(255)   not null,
    DELIVERY_COST    decimal(10, 2) not null
);

create table if not exists TB_FILE
(
    FILE_ID   int AUTO_INCREMENT not null comment 'AUTO_INCREMENT'
        primary key,
	MENU_CD	varchar(4)	NOT NULL comment '메뉴코드',
	FILE_NEW_NAME	varchar(30)	NOT NULL,
	FILE_NAME	varchar(30)	NOT NULL,
	FILE_SIZE	varchar(255)	NOT NULL,
	FILE_TYPE	varchar(2)	NOT NULL	COMMENT '01: 서버, 02: 스토리지서버, 03: blob',
	FILE_PATH	varchar(255)	NULL,
	FILE_EXTENSION	varchar(255)	NULL,
	REG_ID		varchar(50)	not null,
	REG_DT      timestamp   not null
);

create table if not exists TB_INVOICE
(
    INVOICE_ID      int         not null comment 'AUTO_INCREMENT'
        primary key,
    ORDER_ID        int         not null comment 'AUTO_INCREMENT',
    INVOICE_CODE    varchar(30) not null,
    INVOICE_COMPANY varchar(30) not null,
    REG_DT          timestamp   not null,
    MOD_DT          timestamp   not null
);

create table if not exists TB_LIKE
(
    LIKE_ID    int         not null comment 'AUTO_INCREMENT'
        primary key,
    USER_ID    varchar(50) not null,
    PRODUCT_ID bigint      not null,
    REG_DT     timestamp   not null
);

create table if not exists TB_LOGIN
(
    USER_ID      varchar(50)  not null
        primary key,
    ROLE_ID      int          not null,
    PASSWORD     varchar(100) not null,
    NAME         varchar(30)  not null,
    GENDER_CD    varchar(2)   not null comment '1: 여, 2: 남',
    EMAIL        varchar(30)  not null,
    PHONE        varchar(14)  not null,
    ADDRESS      varchar(30)  not null,
    SOCIAL_ID    varchar(100) null,
    DEFAULT_ADDR int          null,
    REG_ID       varchar(50)  null,
    REG_DT       timestamp    null,
    MOD_ID       varchar(50)  null,
    MOD_DT       timestamp    null
);

create table if not exists TB_LOGIN_HIS
(
    LOGIN_HIS_ID int          not null comment 'AUTO_INCREMENT'
        primary key,
    USER_ID      varchar(255) null,
    NAME         varchar(30)  not null,
    IP           varchar(20)  not null,
    REG_ID       varchar(50)  null,
    REG_DT       timestamp    null
);

create table if not exists TB_MENU
(
    MENU_CD          varchar(8)           not null primary key,
    MENU_GROUP_CD    varchar(8)           not null,
    MENU_NAME        varchar(30)          not null,
    MENU_URL         varchar(100)         not null,
    MENU_DEFAULT_URL varchar(100)         null,
    TMPL_CD          varchar(4)           null,
    IS_ADMIN         tinyint(1) default 0 not null,
    ICON_HTML        text                 null
);

create table if not exists TB_ORDER
(
    ORDER_ID      int           not null comment 'AUTO_INCREMENT'
        primary key,
    USER_ID       varchar(50)   not null,
    PRODUCT_ID    int           not null,
    COUPON_ID     bigint        not null comment 'AUTO_INCREMENT',
    PP_ID         int           not null comment 'AUTO_INCREMENT',
    TOTAL_PRICE   int           not null,
    ORDER_COMMENT varchar(2000) null,
    ADDRESS_NAME  varchar(20)   not null,
    ADDRESS       varchar(100)  not null,
    PHONE         varchar(11)   not null,
    REG_DT        timestamp     not null,
    DELIVERY      int           null comment '0:무료, 1:일반, 2:특수',
    ORDER_STATE   tinyint       null
);

create table if not exists TB_ORDER_DETAIL
(
    ORDER_DETAIL_ID int not null comment 'AUTO_INCREMENT'
        primary key,
    PRODUCT_ID      int not null comment 'AUTO_INCREMENT',
    ORDER_ID        int not null comment 'AUTO_INCREMENT',
    PRICE           int not null,
    QUANTITY        int not null
);

create table if not exists TB_PAYMENT_PLAN
(
    PP_ID   int         not null comment 'AUTO_INCREMENT'
        primary key,
    PP_PLAN varchar(30) not null
);

create table if not exists TB_PAY_BANK
(
    BANK_ID        int         not null,
    PP_ID          int         not null comment 'AUTO_INCREMENT',
    BANK_NAME      varchar(30) not null,
    BANK_CODE      int         not null,
    ORDER_AMOUNT   int         not null,
    DEPOSIT_AMOUNT int         null,
    DEPOSIT_DT     datetime    null,
    EXPIRATION_DT  datetime    not null,
    PAYMENT_STATE  tinyint     not null,
    primary key (BANK_ID, PP_ID),
    constraint FK_TB_PAYMENT_PLAN_TO_TB_PAY_BANK_1
        foreign key (PP_ID) references TB_PAYMENT_PLAN (PP_ID)
);

create table if not exists TB_PAY_CARD
(
    PC_ID           int         not null comment 'AUTO_INCREMENT',
    PP_ID           int         not null comment 'AUTO_INCREMENT',
    CARD_COMPANY    varchar(30) not null,
    CARD_COMPANY_CD varchar(2)  not null,
    ORDER_AMOUNT    int         not null,
    PAYMENT_AMOUNT  int         not null,
    REG_DT          timestamp   not null,
    PAYMENT_STATE   varchar(2)  not null,
    primary key (PC_ID, PP_ID),
    constraint FK_TB_PAYMENT_PLAN_TO_TB_PAY_CARD_1
        foreign key (PP_ID) references TB_PAYMENT_PLAN (PP_ID)
);

create table if not exists TB_PRODUCT
(
    PRODUCT_ID    int          not null comment 'AUTO_INCREMENT'
        primary key,
    PRODUCT_NAME  varchar(255) not null,
    PRICE         int          not null,
    CONTENT       json         not null comment '상품의 상세정보와 이미지들이 json 형식으로 들어감',
    STOCKS        int          not null,
    SALES         bigint       not null,
    STATUS        varchar(50)  not null,
    WATT          varchar(8)   null,
    STOCK         int          null,
    DISCOUNT_RATE int          null
);

create table if not exists TB_PRODUCT_OPTION
(
    OPTION_ID  int         not null comment 'AUTO_INCREMENT'
        primary key,
    `OPTION`   varchar(50) not null,
    CONTENT    varchar(50) null,
    PRICE      int         not null,
    REG_ID     timestamp   not null,
    REG_DT     varchar(50) not null,
    STATUS     varchar(50) not null comment '일반',
    PRODUCT_ID int         not null comment 'AUTO_INCREMENT'
);

create table if not exists TB_PRODUCT_QNA
(
    QNA_ID          varchar(255) not null
        primary key,
    PRODUCT_ID      int          not null comment 'AUTO_INCREMENT',
    USER_ID         varchar(50)  not null,
    QUESTION        text         not null,
    ANSWER          text         null,
    ANSWER_YN       char         not null comment '{Y: 답변 있음, N: 답변 없음}',
    QNA_CATEGORY_ID int          null,
    REG_DT          timestamp    not null,
    MOD_DT          timestamp    null,
    ANS_REG_DT      timestamp    null,
    ANS_REG_ID      timestamp    null,
    ANS_MOD_DT      timestamp    null,
    ANS_MOD_ID      timestamp    null
);

create table if not exists TB_REVIEW
(
    REVIEW_ID     bigint        not null comment 'AUTO_INCREMENT'
        primary key,
    PRODUCT_ID    int           not null comment 'AUTO_INCREMENT',
    USER_ID       varchar(50)   not null,
    RATING        decimal(2, 1) not null comment '0~5점(0.5 단위)',
    REVIEW_TEXT   varchar(255)  null,
    REVIEW_DATE   datetime      not null,
    LIKES         int           not null,
    REVIEW_PHOTOS json          null,
    ADMIN_NOTES   varchar(255)  null comment '검토나 삭제에 대한 계획'
);

create table if not exists TB_ROLE
(
    ROLE_ID     int auto_increment comment 'AUTO_INCREMENT'
        primary key,
    ROLE_NAME   varchar(50)  not null,
    ROLE_TYPE   varchar(8)   null,
    DESCRIPTION varchar(200) null,
    REG_ID      varchar(50)  null,
    REG_DT      timestamp    null,
    MOD_ID      varchar(50)  null,
    MOD_DT      timestamp    null
);

create table if not exists TB_ROLE_MENU
(
    ROLE_ID int        not null,
    MENU_CD varchar(8) not null,
    MAIN_YN varchar(1) not null
);

create table if not exists TB_TAG
(
    TAG_ID     bigint      not null
        primary key,
    PRODUCT_ID int         not null comment 'AUTO_INCREMENT',
    TAG_NAME   varchar(50) not null
);

create table if not exists TB_TAG_MAP
(
    PRODUCT_ID int    not null comment 'AUTO_INCREMENT',
    TAG_ID     bigint not null
);

CREATE TABLE `TB_ADDRESS`
(
    `ADDR_ID`      int AUTO_INCREMENT NOT NULL primary key,
    `ADDR_NAME`    varchar(100)       NOT NULL,
    `RECEIVER`     varchar(50)        NOT NULL,
    `PHONE_NUMBER` varchar(14)        NOT NULL,
    `ZIP_CD`       int                NOT NULL,
    `BASE_ADDR`    varchar(100)       NOT NULL,
    `DETAIL_ADDR`  varchar(50)        NOT NULL,
    `REG_DT`       timestamp          NOT NULL,
    `USER_ID`      varchar(50)        NOT NULL
);


ALTER TABLE `TB_LOGIN`
    ADD CONSTRAINT `FK_TB_ROLE_TO_TB_LOGIN_1` FOREIGN KEY (
                                                           `ROLE_ID`
        )
        REFERENCES `TB_ROLE` (
                              `ROLE_ID`
            );

ALTER TABLE `TB_LIKE`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_LIKE_1` FOREIGN KEY (
                                                           `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_TAG`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_TAG_1` FOREIGN KEY (
                                                            `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_ROLE_MENU`
    ADD CONSTRAINT `FK_TB_MENU_TO_TB_ROLE_MENU_1` FOREIGN KEY (
                                                               `MENU_CD`
        )
        REFERENCES `TB_MENU` (
                              `MENU_CD`
            );

ALTER TABLE `TB_COLOR`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_COLOR_1` FOREIGN KEY (
                                                              `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_COUPON`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_COUPON_1` FOREIGN KEY (
                                                             `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_CARTS`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_CARTS_1` FOREIGN KEY (
                                                            `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_CARTS`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_CARTS_1` FOREIGN KEY (
                                                              `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_CARTS`
    ADD CONSTRAINT `FK_TB_PRODUCT_OPTION_TO_TB_CARTS_1` FOREIGN KEY (
                                                                     `OPTION_ID`
        )
        REFERENCES `TB_PRODUCT_OPTION` (
                                        `OPTION_ID`
            );

ALTER TABLE `TB_PRODUCT_OPTION`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_PRODUCT_OPTION_1` FOREIGN KEY (
                                                                       `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_FILE`
    ADD CONSTRAINT `FK_TB_BOARD_TO_TB_FILE_1` FOREIGN KEY (
                                                           `BOARD_ID`
        )
        REFERENCES `TB_BOARD` (
                               `BOARD_ID`
            );

ALTER TABLE `TB_ORDER`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_ORDER_1` FOREIGN KEY (
                                                            `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_ORDER`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_ORDER_1` FOREIGN KEY (
                                                              `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_ORDER`
    ADD CONSTRAINT `FK_TB_COUPON_TO_TB_ORDER_1` FOREIGN KEY (
                                                             `COUPON_ID`
        )
        REFERENCES `TB_COUPON` (
                                `COUPON_ID`
            );

ALTER TABLE `TB_ORDER`
    ADD CONSTRAINT `FK_TB_PAYMENT_PLAN_TO_TB_ORDER_1` FOREIGN KEY (
                                                                   `PP_ID`
        )
        REFERENCES `TB_PAYMENT_PLAN` (
                                      `PP_ID`
            );

ALTER TABLE `TB_REVIEW`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_REVIEW_1` FOREIGN KEY (
                                                               `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_REVIEW`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_REVIEW_1` FOREIGN KEY (
                                                             `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_ORDER_DETAIL`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_ORDER_DETAIL_1` FOREIGN KEY (
                                                                     `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_ORDER_DETAIL`
    ADD CONSTRAINT `FK_TB_ORDER_TO_TB_ORDER_DETAIL_1` FOREIGN KEY (
                                                                   `ORDER_ID`
        )
        REFERENCES `TB_ORDER` (
                               `ORDER_ID`
            );

ALTER TABLE `TB_DELIVERY`
    ADD CONSTRAINT `FK_TB_INVOICE_TO_TB_DELIVERY_1` FOREIGN KEY (
                                                                 `INVOICE_ID`
        )
        REFERENCES `TB_INVOICE` (
                                 `INVOICE_ID`
            );

ALTER TABLE `TB_INVOICE`
    ADD CONSTRAINT `FK_TB_ORDER_TO_TB_INVOICE_1` FOREIGN KEY (
                                                              `ORDER_ID`
        )
        REFERENCES `TB_ORDER` (
                               `ORDER_ID`
            );


ALTER TABLE `TB_COUPON_USE_HIS`
    ADD CONSTRAINT `FK_TB_COUPON_TO_TB_COUPON_USE_HIS_1` FOREIGN KEY (
                                                                      `COUPON_ID`
        )
        REFERENCES `TB_COUPON` (
                                `COUPON_ID`
            );

ALTER TABLE `TB_PRODUCT_QNA`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_PRODUCT_QNA_1` FOREIGN KEY (
                                                                    `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_PRODUCT_QNA`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_PRODUCT_QNA_1` FOREIGN KEY (
                                                                  `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

ALTER TABLE `TB_CATEGORY_MAP`
    ADD CONSTRAINT `FK_TB_CATEGORY_TO_TB_CATEGORY_MAP_1` FOREIGN KEY (
                                                                      `CATEGORY_ID`
        )
        REFERENCES `TB_CATEGORY` (
                                  `CATEGORY_ID`
            );

ALTER TABLE `TB_CATEGORY_MAP`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_CATEGORY_MAP_1` FOREIGN KEY (
                                                                     `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_TAG_MAP`
    ADD CONSTRAINT `FK_TB_PRODUCT_TO_TB_TAG_MAP_1` FOREIGN KEY (
                                                                `PRODUCT_ID`
        )
        REFERENCES `TB_PRODUCT` (
                                 `PRODUCT_ID`
            );

ALTER TABLE `TB_TAG_MAP`
    ADD CONSTRAINT `FK_TB_TAG_TO_TB_TAG_MAP_1` FOREIGN KEY (
                                                            `TAG_ID`
        )
        REFERENCES `TB_TAG` (
                             `TAG_ID`
            );

ALTER TABLE `TB_ADDRESS`
    ADD CONSTRAINT `FK_TB_LOGIN_TO_TB_ADDRESS_1` FOREIGN KEY (
                                                              `USER_ID`
        )
        REFERENCES `TB_LOGIN` (
                               `USER_ID`
            );

