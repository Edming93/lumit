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
       ('M202', 'M200', '회원관리', '/admin/user', '', '', 1, '<i class=\"fa-solid fa-user\"></i>'),
       ('M203', 'M200', '배송지관리', '/admin/addr/**', 'list', '', 1, '<i class=\"fa-solid fa-location-dot\"></i>'),
       ('M204', 'M200', '결제관리', '/admin/payment/**', 'list', '', 1, '<i class=\"fa-solid fa-wallet\"></i>'),
       ('M205', 'M200', '환불관리', '/admin/refund/**', 'list', '', 1, '<i class=\"fa-solid fa-rotate-left\"></i>'),
       ('M206', 'M200', '배송관리', '/admin/delivery/**', 'list', '', 1, '<i class=\"fa-solid fa-truck\"></i>'),
       ('M207', 'M200', '쿠폰관리', '/admin/coupon/**', 'list', '', 1, '<i class=\"fa-solid fa-ticket-simple\"></i>'),
       ('M208', 'M200', '공지사항', '/admin/board/M208/**', 'list', '0000', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M209', 'M200', 'Q&A', '/admin/board/M209/**', 'list', '0001', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M210', 'M200', 'FAQ', '/admin/board/M210/**', 'list', '0002', 1, '<i class="fa-regular fa-clipboard"></i>'),
       ('M211', 'M200', '주문관리', '/admin/order/**', 'dashboard', '', 1, '<i class=\"fa-solid fa-cart-shopping\"></i>'),
       ('M212', 'M200', '옵션관리', '/admin/option/**', 'list', '', 1, '<i class="fa-solid fa-wrench"></i>'),
       ('M213', 'M200', '분류관리', '/admin/category/**', 'list', '', 1, '<i class="fa-solid fa-list"></i>');


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
       (1, 'M212', 'N'),
       (1, 'M213', 'N'),
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


INSERT IGNORE INTO `TB_CODE` (`GRP_CD`, `CD`, `GRP_CD_NM`, `CD_NM`, `SORT_SEQ`, `USE_YN`, `CTT`, `REG_ID`, `REG_DT`,
                              `MOD_ID`, `MOD_DT`)
VALUES ('CATEGORIES', '0000', '메뉴구분코드', '상품문의', 1, 'Y', NULL, 'admin', '2024-11-18 22:41:19', 'admin',
        '2024-11-18 22:41:13'),
       ('CATEGORIES', '0001', '메뉴구분코드', '교환&반품문의', 2, 'Y', NULL, 'admin', '2024-11-18 22:42:57', 'admin',
        '2024-11-18 22:41:19'),
       ('CATEGORIES', '0002', '메뉴구분코드', '배송문의', 3, 'Y', NULL, 'admin', '2024-11-18 22:43:02', 'admin',
        '2024-11-18 22:42:40'),
       ('DEL_YN', 'N', '삭제여부', '미삭제', 2, 'Y', NULL, 'admin', '2025-01-05 16:04:57', 'admin', '2025-01-05 16:04:57'),
       ('DEL_YN', 'Y', '삭제여부', '삭제', 1, 'Y', NULL, 'admin', '2025-01-05 16:04:20', 'admin', '2025-01-05 16:04:20'),
       ('DP_STATUS', 'N', '진열여부', '진열안함', 2, 'Y', NULL, 'admin', '2025-01-05 16:05:54', 'admin', '2025-01-05 16:05:54'),
       ('DP_STATUS', 'Y', '진열여부', '진열함', 1, 'Y', NULL, 'admin', '2025-01-05 16:05:54', 'admin', '2025-01-05 16:05:54'),
       ('PD_STATUS', '0000', '판매상태', '판매함', 1, 'Y', NULL, 'admin', '2025-01-05 15:58:16', 'admin',
        '2024-11-18 22:42:40'),
       ('PD_STATUS', '0001', '판매상태', '판매안함 (품절)', 2, 'Y', NULL, 'admin', '2025-01-05 15:58:12', 'admin',
        '2024-11-18 22:42:40'),
       ('OPTION_DV_CD', '0000', '옵션구분코드', '태그', 1, 'Y', NULL, 'admin', '2025-01-05 15:58:12', 'admin',
        '2024-11-18 22:42:40'),
       ('OPTION_DV_CD', '0001', '옵션구분코드', '색상', 2, 'Y', NULL, 'admin', '2025-01-05 15:58:12', 'admin',
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

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트1', '<p>내용1</p>', '2996', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트2', '<p>내용2</p>', '8774', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트3', '<p>내용3</p>', '7192', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트4', '<p>내용4</p>', '', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin', NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트5', '<p>내용5</p>', '5685', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트6', '<p>내용6</p>', '', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin', NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트7', '<p>내용7</p>', '4856', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트8', '<p>내용8</p>', '', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin', NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트9', '<p>내용9</p>', '8674', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());

INSERT INTO TB_BOARD(menu_cd, menu_dv_cd, categories, title, content, password, top_fix, use_yn, del_yn, rply_yn,
                     file_yn, view_count, reg_id, reg_dt, mod_id, mod_dt)
VALUES ('M208', '0000', NULL, '테스트10', '<p>내용10</p>', '9189', NULL, 'N', 'N', 'N', 'N', 0, 'admin', NOW(), 'admin',
        NOW());


INSERT IGNORE INTO `tb_option` (`OPTION_ID`, `OPTION_DV_CD`, `OPTION_NAME`, `OPTION_CD`) VALUES
	(1, '0001', '검정', '#000000'),
	(2, '0001', '핑크', '#ffcccc'),
	(3, '0001', '노랑', '#fdff9e'),
	(4, '0000', '따뜻한', NULL),
	(5, '0000', '평온한', NULL),
	(6, '0000', '차가운', NULL),
	(7, '0000', '윤기있는', NULL);

INSERT IGNORE INTO `tb_category` (`CATEGORY_ID`, `CATEGORY_NAME`, `USE_YN`, `PARENT`, `DEPTH`, `REG_ID`, `REG_DT`, `MOD_ID`, `MOD_DT`) VALUES
	(1, '서울', 'Y', 0, 0, 'admin', '2025-08-14 16:27:40', NULL, NULL),
	(2, '경기도', 'Y', 0, 0, 'admin', '2025-08-14 16:27:42', NULL, NULL),
	(3, '인천', 'Y', 0, 0, 'admin', '2025-08-14 16:27:44', NULL, NULL),
	(4, '대구', 'Y', 0, 0, 'admin', '2025-08-14 16:27:46', NULL, NULL),
	(5, '충청도', 'Y', 0, 0, 'admin', '2025-08-14 16:27:49', NULL, NULL),
	(6, '전라도', 'Y', 0, 0, 'admin', '2025-08-14 16:27:52', NULL, NULL),
	(7, '강남구', 'Y', 1, 1, 'admin', '2025-08-14 16:27:58', NULL, NULL),
	(8, '강북구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:01', NULL, NULL),
	(9, '강서구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:03', NULL, NULL),
	(10, '강동구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:05', NULL, NULL),
	(11, '양천구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:07', NULL, NULL),
	(12, '금천구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:09', NULL, NULL),
	(13, '구로구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:11', NULL, NULL),
	(14, '부천', 'Y', 2, 1, 'admin', '2025-08-14 16:28:15', 'admin', '2025-08-14 16:28:19'),
	(15, '양재동', 'Y', 7, 2, 'admin', '2025-08-14 16:28:26', NULL, NULL),
	(16, '삼성동', 'Y', 7, 2, 'admin', '2025-08-14 16:28:34', NULL, NULL),
	(17, '마곡', 'Y', 9, 2, 'admin', '2025-08-14 16:28:38', NULL, NULL),
	(18, '양평', 'Y', 9, 2, 'admin', '2025-08-14 16:28:40', NULL, NULL),
	(19, '영등포구', 'Y', 1, 1, 'admin', '2025-08-14 16:28:47', NULL, NULL),
	(20, '화곡', 'Y', 9, 2, 'admin', '2025-08-14 16:28:52', NULL, NULL),
	(21, '신정', 'Y', 9, 2, 'admin', '2025-08-14 16:28:56', NULL, NULL),
	(22, '신월동', 'Y', 11, 2, 'admin', '2025-08-14 16:29:19', NULL, NULL),
	(23, '목동', 'Y', 11, 2, 'admin', '2025-08-14 16:29:21', NULL, NULL),
	(24, '바밤바', 'Y', 15, 3, 'admin', '2025-08-14 16:29:25', NULL, NULL),
	(25, '별난바', 'Y', 15, 3, 'admin', '2025-08-14 16:29:38', NULL, NULL),
	(26, '쌍쌍바', 'Y', 15, 3, 'admin', '2025-08-14 16:29:40', NULL, NULL),
	(27, '토리', 'Y', 16, 3, 'admin', '2025-08-14 16:29:43', NULL, NULL),
	(28, '요정', 'Y', 16, 3, 'admin', '2025-08-14 16:29:47', NULL, NULL),
	(29, '파스타', 'Y', 17, 3, 'admin', '2025-08-14 16:29:54', NULL, NULL),
	(30, '피자', 'Y', 17, 3, 'admin', '2025-08-14 16:29:56', NULL, NULL),
	(31, '삼겹살', 'Y', 17, 3, 'admin', '2025-08-14 16:30:00', NULL, NULL);
