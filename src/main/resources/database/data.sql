REPLACE INTO `TB_ROLE`
VALUES (1, 'SUPER_ADMIN', 'R100', '슈퍼관리자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09'),
       (2, 'ADMIN', 'R100', '관리자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09'),
       (3, 'USER', 'R100', '일반사용자', 'admin', '2024-07-17 10:32:09', 'admin', '2024-07-17 10:32:09');

REPLACE INTO `TB_ROLE_MENU`
VALUES (1, 'M100', 'N'),
       (1, 'M110', 'N'),
       (1, 'M120', 'N'),
       (1, 'M130', 'N'),
       (1, 'M140', 'N'),
       (1, 'M200', 'Y'),
       (1, 'M210', 'N'),
       (1, 'M220', 'N'),
       (1, 'M230', 'N'),
       (1, 'M240', 'N'),
       (1, 'M250', 'N'),
       (1, 'M260', 'N'),
       (1, 'M270', 'N'),
       (1, 'M280', 'N'),
       (1, 'M290', 'N'),
       (2, 'M100', 'N'),
       (2, 'M110', 'N'),
       (2, 'M120', 'N'),
       (2, 'M130', 'N'),
       (2, 'M140', 'N'),
       (2, 'M200', 'Y'),
       (2, 'M210', 'N'),
       (2, 'M220', 'N'),
       (2, 'M230', 'N'),
       (2, 'M240', 'N'),
       (2, 'M250', 'N'),
       (2, 'M260', 'N'),
       (2, 'M270', 'N'),
       (2, 'M280', 'N'),
       (2, 'M290', 'N'),
       (3, 'M100', 'Y'),
       (3, 'M110', 'N'),
       (3, 'M120', 'N'),
       (3, 'M130', 'N'),
       (3, 'M140', 'N');

REPLACE INTO `TB_LOGIN`
VALUES ('admin', 2, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'ssdd', '1', 'sadf@dfa.com',
        '01030303848', 'dfjaksdf', 'afddasffd', now(), NULL, now()),
       ('user', 3, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'asdf', '1', 'asdf@asdf.asd',
        '01012341234', 'asdf', 'asdf', now(), NULL, now()),
       ('dorong', 1, '$2a$10$6hXP0sf0sQb85MK/kAtuz.uZmKNou.PboQYLdZDi2DkrgCtTYreW6', 'aaaa', '1', 'aaaa@aaa.com',
        '01000000000', 'asdf', 'asdf', now(), NULL, now());

REPLACE INTO `TB_MENU`
VALUES ('M100', 'M100', '메인', '/lumit/**', '', 0, NULL),
       ('M110', 'M100', '인테리어', '/lumit/interior/**', 'list', 0, NULL),
       ('M120', 'M100', '스튜디오', '/lumit/studio/**', 'list', 0, NULL),
       ('M130', 'M100', '질문게시판', '/lumit/question/**', 'list', 0, NULL),
       ('M140', 'M100', '공지사항', '/lumit/notice/**', 'list', 0, NULL),
       ('M200', 'M200', '메인', '/admin/**', '', 0, '<i class=\"fa-solid fa-house\"></i>'),
       ('M210', 'M200', '상품관리', '/admin/product/**', 'list', 1, '<i class=\"fa-solid fa-shirt\"></i>'),
       ('M220', 'M200', '회원관리', '/admin/member/**', 'list', 1, '<i class=\"fa-solid fa-user\"></i>'),
       ('M230', 'M200', '배송지관리', '/admin/addr/**', 'list', 1, '<i class=\"fa-solid fa-location-dot\"></i>'),
       ('M240', 'M200', '결제관리', '/admin/payment/**', 'list', 1, '<i class=\"fa-solid fa-wallet\"></i>'),
       ('M250', 'M200', '환불관리', '/admin/refund/**', 'list', 1, '<i class=\"fa-solid fa-rotate-left\"></i>'),
       ('M260', 'M200', '배송관리', '/admin/delivery/**', 'list', 1, '<i class=\"fa-solid fa-truck\"></i>'),
       ('M270', 'M200', '쿠폰관리', '/admin/coupon/**', 'list', 1, '<i class=\"fa-solid fa-ticket-simple\"></i>'),
       ('M280', 'M200', '공지사항', '/admin/notice/**', 'list', 1, '<i class=\"fa-solid fa-microphone\"></i>'),
       ('M290', 'M200', '주문관리', '/admin/order/**', 'dashboard', 1, '<i class=\"fa-solid fa-cart-shopping\"></i>');