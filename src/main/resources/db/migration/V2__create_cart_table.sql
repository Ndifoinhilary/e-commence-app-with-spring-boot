create table carts
(
    id           binary(16) default (uuid_to_bin(uuid()))  primary key not null,
    date_created date        default (curdate())           not null
);

create table cart_items
(
    id         bigint auto_increment
        primary key,
    product_id bigint        not null,
    cart_id    binary(16)    not null,
    quantity   int default 1 not null,
    constraint cart_items_cart_product_unique
        unique (product_id, cart_id),
    constraint cart_items_carts_id_fk
        foreign key (cart_id) references carts (id)
            on delete cascade,
    constraint cart_items_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade
);
