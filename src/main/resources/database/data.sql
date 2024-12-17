REPLACE INTO `TB_ROLE`
VALUES (1, 'SUPER_ADMIN', 'R100', '슈퍼관리자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09'),
       (2, 'ADMIN', 'R100', '관리자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09'),
       (3, 'USER', 'R100', '일반사용자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09');


REPLACE INTO `TB_LOGIN`
VALUES ('admin', 1, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'ssdd', '1', 'sadf@dfa.com',
        '01030303848', 'dfjaksdf', NULL, NULL, 1, NULL, 'admin', now(), 'admin', now()),
       ('user', 3, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'asdf', '1', 'asdf@asdf.asd',
        '01012341234', 'asdf', NULL, NULL, 1, NULL, 'admin', now(), 'admin', now()),
       ('dorong', 2, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'aaaa', '1', 'aaaa@aaa.com',
        '01000000000', 'asdf', NULL, NULL, 1, NULL, 'admin', now(), 'admin', now());

REPLACE INTO `TB_MENU`
VALUES ('M100', 'M100', '메인', '/main/**', '', '', 0, NULL),
       ('M101', 'M100', '인테리어', '/main/interior/**', 'list', '', 0, NULL),
       ('M102', 'M100', '스튜디오', '/main/studio/**', 'list', '', 0, NULL),
       ('M103', 'M100', '질문게시판', '/main/board/M209/**', 'list', '', 0, NULL),
       ('M104', 'M100', '공지사항', '/main/board/M208/**', 'list', '', 0, NULL),
       ('M105', 'M100', '회원정보', '/main/member/**', '', '', 0, NULL),
       ('M200', 'M200', '메인', '/admin/**', '', '', 0, '<i class=\"fa-solid fa-house\"></i>'),
       ('M201', 'M200', '상품관리', '/admin/product/**', 'list', '', 1, '<i class=\"fa-solid fa-shirt\"></i>'),
       ('M202', 'M200', '회원관리', '/admin/member/**', 'list', '', 1, '<i class=\"fa-solid fa-user\"></i>'),
       ('M203', 'M200', '배송지관리', '/admin/addr/**', 'list', '', 1, '<i class=\"fa-solid fa-location-dot\"></i>'),
       ('M204', 'M200', '결제관리', '/admin/payment/**', 'list', '', 1, '<i class=\"fa-solid fa-wallet\"></i>'),
       ('M205', 'M200', '환불관리', '/admin/refund/**', 'list', '', 1, '<i class=\"fa-solid fa-rotate-left\"></i>'),
       ('M206', 'M200', '배송관리', '/admin/delivery/**', 'list', '', 1, '<i class=\"fa-solid fa-truck\"></i>'),
       ('M207', 'M200', '쿠폰관리', '/admin/coupon/**', 'list', '', 1, '<i class=\"fa-solid fa-ticket-simple\"></i>'),
       ('M208', 'M200', '공지사항', '/admin/board/M208/**', 'list', '0000', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M209', 'M200', 'Q&A', '/admin/board/M209/**', 'list', '0001', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M210', 'M200', 'FAQ', '/admin/board/M210/**', 'list', '0002', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M211', 'M200', '주문관리', '/admin/order/**', 'dashboard', '', 1, '<i class=\"fa-solid fa-cart-shopping\"></i>');


REPLACE INTO `TB_ROLE_MENU`
VALUES (1, 'M100', 'N'),
       (1, 'M101', 'N'),
       (1, 'M102', 'N'),
       (1, 'M103', 'N'),
       (1, 'M104', 'N'),
       (1, 'M200', 'Y'),
       (1, 'M201', 'N'),
       (1, 'M202', 'N'),
       (1, 'M203', 'N'),
       (1, 'M204', 'N'),
       (1, 'M205', 'N'),
       (1, 'M206', 'N'),
       (1, 'M207', 'N'),
       (1, 'M208', 'N'),
       (1, 'M209', 'N'),
       (1, 'M210', 'N'),
       (1, 'M211', 'N'),
       (2, 'M100', 'N'),
       (2, 'M101', 'N'),
       (2, 'M102', 'N'),
       (2, 'M103', 'N'),
       (2, 'M104', 'N'),
       (2, 'M200', 'Y'),
       (2, 'M201', 'N'),
       (2, 'M202', 'N'),
       (2, 'M203', 'N'),
       (2, 'M204', 'N'),
       (2, 'M205', 'N'),
       (2, 'M206', 'N'),
       (2, 'M207', 'N'),
       (2, 'M208', 'N'),
       (2, 'M209', 'N'),
       (2, 'M210', 'N'),
       (2, 'M211', 'N'),
       (3, 'M100', 'Y'),
       (3, 'M101', 'N'),
       (3, 'M102', 'N'),
       (3, 'M103', 'N'),
       (3, 'M104', 'N'),
       (3, 'M105', 'N');


INSERT INTO `TB_CODE` (`GRP_CD`, `CD`, `GRP_CD_NM`, `CD_NM`, `SORT_SEQ`, `USE_YN`, `CTT`, `REG_ID`, `REG_DT`, `MOD_ID`,
                       `MOD_DT`)
VALUES ('CATEGORIES', '0000', '메뉴구분코드', '상품문의', 1, 'Y', NULL, 'admin', '2024-11-18 22:41:19', 'admin',
        '2024-11-18 22:41:13');
INSERT INTO `TB_CODE` (`GRP_CD`, `CD`, `GRP_CD_NM`, `CD_NM`, `SORT_SEQ`, `USE_YN`, `CTT`, `REG_ID`, `REG_DT`, `MOD_ID`,
                       `MOD_DT`)
VALUES ('CATEGORIES', '0001', '메뉴구분코드', '교환&반품문의', 2, 'Y', NULL, 'admin', '2024-11-18 22:42:57', 'admin',
        '2024-11-18 22:41:19');
INSERT INTO `TB_CODE` (`GRP_CD`, `CD`, `GRP_CD_NM`, `CD_NM`, `SORT_SEQ`, `USE_YN`, `CTT`, `REG_ID`, `REG_DT`, `MOD_ID`,
                       `MOD_DT`)
VALUES ('CATEGORIES', '0002', '메뉴구분코드', '배송문의', 3, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40');
INSERT INTO `TB_CODE` (`GRP_CD`, `CD`, `GRP_CD_NM`, `CD_NM`, `SORT_SEQ`, `USE_YN`, `CTT`, `REG_ID`, `REG_DT`, `MOD_ID`,
                       `MOD_DT`)
VALUES ('PD_STATUS', '0000', '판매상태', '신상품', 1, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0001', '판매상태', '중고상품', 2, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0002', '판매상태', '반품상품', 3, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0004', '판매상태', '재고상품', 4, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0005', '판매상태', '전시상품', 5, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0006', '판매상태', '리퍼상품', 6, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
        ('PD_STATUS', '0007', '판매상태', '스크래치상품', 7, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40');
        
        
INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0000',
        'U0000',
        '홈',
        '/main/member',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0001',
        'U0000',
        '최근 주문 내역',
        '/main/member/order',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0100',
        'U0000',
        '주문 내역 조회',
        NULL,
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0101',
        'U0000',
        '주문/배송 조회',
        '/main/member/order/list',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0102',
        'U0000',
        '취소/교환/반품 조회',
        '/main/member/order/cancel-list',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0200',
        'U0000',
        '쇼핑 혜택',
        NULL,
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0201',
        'U0000',
        '쿠폰',
        '/main/member/order/coupon',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0300',
        'U0000',
        '활동',
        NULL,
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0301',
        'U0000',
        '리뷰관리',
        '/main/member/order/review',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0302',
        'U0000',
        '1:1 문의',
        '/main/member/order/qna',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0303',
        'U0000',
        '찜 리스트',
        '/main/member/heart',
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0400',
        'U0000',
        '회원정보',
        NULL,
        NULL,
        NULL,
        0,
        NULL);

INSERT INTO TB_MENU
(MENU_CD,
 MENU_GROUP_CD,
 MENU_NAME,
 MENU_URL,
 MENU_DEFAULT_URL,
 TMPL_CD,
 IS_ADMIN,
 ICON_HTML)
VALUES ('U0401',
        'U0000',
        '회원정보 수정',
        '/main/member/edit',
        NULL,
        NULL,
        0,
        NULL);

