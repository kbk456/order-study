-- item
create table items
(
    id         bigint auto_increment primary key comment 'ID',
    item_no    varchar(30) not null comment '상품 번호',
    item_name  varchar(255) not null comment '상품명',
    item_price int(7) not null comment '상품 가격',
    item_stock int(5) not null comment '재고 수량',
    status     varchar(30)  not null default 'ON_SALE' comment '상태',
    version bigint null comment '버전 관리',
    created_at datetime(6) not null comment '생성 일시',
    updated_at datetime(6) null comment '수정 일시'
) comment 'items' charset = utf8mb4;

create
    index items_idx01 on items (item_no);
-- order
create table orders
(
    id                bigint auto_increment primary key comment 'ID',
    user_id           bigint       not null comment '유저 ID',
    status            varchar(30)  not null default 'INIT' comment '상태',
    ordered_at        datetime(6) not null comment '주문 일시',
    created_at        datetime(6) null comment '생성 일시',
    updated_at        datetime(6) null comment '수정 일시'
) comment 'orders' charset = utf8mb4;

-- order_items
create table order_items
(
    id              bigint auto_increment primary key comment 'ID',
    order_id        bigint       not null comment 'order_id',
    order_count     tinyint      not null comment '주문갯수',
    item_no         bigint       not null comment '상품 번호',
    item_name       varchar(255) not null comment '상품명',
    item_price      int(7) not null comment '상품 가격',
    delivery_status varchar(30)  not null default 'BEFORE_DELIVERY' comment '상태',
    created_at      datetime(6)  null comment '생성 일시',
    updated_at      datetime(6) null comment '수정 일시'
) comment 'order_items' charset = utf8mb4;
