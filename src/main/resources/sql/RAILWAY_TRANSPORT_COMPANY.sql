PGDMP  8                 
    {            RAILWAY_TRANSPORT_COMPANY    16.0    16.0 E    3           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            4           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            5           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            6           1262    16470    RAILWAY_TRANSPORT_COMPANY    DATABASE     �   CREATE DATABASE "RAILWAY_TRANSPORT_COMPANY" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
 +   DROP DATABASE "RAILWAY_TRANSPORT_COMPANY";
                postgres    false            �            1259    16471    roles    TABLE     _   CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(45) NOT NULL
);
    DROP TABLE public.roles;
       public         heap    postgres    false            �            1259    16474    Roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Roles_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."Roles_id_seq";
       public          postgres    false    215            7           0    0    Roles_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Roles_id_seq" OWNED BY public.roles.id;
          public          postgres    false    216            �            1259    16475 	   schedules    TABLE     �   CREATE TABLE public.schedules (
    id bigint NOT NULL,
    train_id bigint NOT NULL,
    arrival_time timestamp without time zone NOT NULL,
    occupied_seats integer NOT NULL,
    departure_time timestamp without time zone NOT NULL
);
    DROP TABLE public.schedules;
       public         heap    postgres    false            �            1259    16478    Schedules_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Schedules_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Schedules_id_seq";
       public          postgres    false    217            8           0    0    Schedules_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."Schedules_id_seq" OWNED BY public.schedules.id;
          public          postgres    false    218            �            1259    16479    stations    TABLE     �   CREATE TABLE public.stations (
    id bigint NOT NULL,
    name character varying(45) NOT NULL,
    city character varying(45) NOT NULL
);
    DROP TABLE public.stations;
       public         heap    postgres    false            �            1259    16482    Stations_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Stations_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."Stations_id_seq";
       public          postgres    false    219            9           0    0    Stations_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Stations_id_seq" OWNED BY public.stations.id;
          public          postgres    false    220            �            1259    16483    tickets    TABLE     �   CREATE TABLE public.tickets (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    schedule_id bigint NOT NULL,
    seat_number integer NOT NULL
);
    DROP TABLE public.tickets;
       public         heap    postgres    false            �            1259    16486    Tickets_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Tickets_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Tickets_id_seq";
       public          postgres    false    221            :           0    0    Tickets_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public."Tickets_id_seq" OWNED BY public.tickets.id;
          public          postgres    false    222            �            1259    16487    trains    TABLE       CREATE TABLE public.trains (
    id bigint NOT NULL,
    departure_station_id bigint NOT NULL,
    arrival_station_id bigint NOT NULL,
    duration interval NOT NULL,
    seats integer NOT NULL,
    train_number character varying NOT NULL,
    current_station_id bigint NOT NULL
);
    DROP TABLE public.trains;
       public         heap    postgres    false            �            1259    16490    Trains_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Trains_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public."Trains_id_seq";
       public          postgres    false    223            ;           0    0    Trains_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."Trains_id_seq" OWNED BY public.trains.id;
          public          postgres    false    224            �            1259    16491    users    TABLE     �   CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(45) NOT NULL,
    password character varying(16) NOT NULL,
    name character varying NOT NULL,
    surname character varying NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16494    Users_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Users_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."Users_id_seq";
       public          postgres    false    225            <           0    0    Users_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Users_id_seq" OWNED BY public.users.id;
          public          postgres    false    226            �            1259    16553 	   roles_seq    SEQUENCE     s   CREATE SEQUENCE public.roles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.roles_seq;
       public          postgres    false            �            1259    16592    schedules_seq    SEQUENCE     w   CREATE SEQUENCE public.schedules_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.schedules_seq;
       public          postgres    false            �            1259    16593    stations_seq    SEQUENCE     v   CREATE SEQUENCE public.stations_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.stations_seq;
       public          postgres    false            �            1259    16594    tickets_seq    SEQUENCE     u   CREATE SEQUENCE public.tickets_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.tickets_seq;
       public          postgres    false            �            1259    16595 
   trains_seq    SEQUENCE     t   CREATE SEQUENCE public.trains_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.trains_seq;
       public          postgres    false            �            1259    16791 
   user_roles    TABLE     ]   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            �            1259    16554 	   users_seq    SEQUENCE     s   CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.users_seq;
       public          postgres    false            s           2604    16607    roles id    DEFAULT     f   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public."Roles_id_seq"'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            t           2604    24983    schedules id    DEFAULT     n   ALTER TABLE ONLY public.schedules ALTER COLUMN id SET DEFAULT nextval('public."Schedules_id_seq"'::regclass);
 ;   ALTER TABLE public.schedules ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217            u           2604    25004    stations id    DEFAULT     l   ALTER TABLE ONLY public.stations ALTER COLUMN id SET DEFAULT nextval('public."Stations_id_seq"'::regclass);
 :   ALTER TABLE public.stations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219            v           2604    25021 
   tickets id    DEFAULT     j   ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public."Tickets_id_seq"'::regclass);
 9   ALTER TABLE public.tickets ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221            w           2604    25046 	   trains id    DEFAULT     h   ALTER TABLE ONLY public.trains ALTER COLUMN id SET DEFAULT nextval('public."Trains_id_seq"'::regclass);
 8   ALTER TABLE public.trains ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223            x           2604    25060    users id    DEFAULT     f   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public."Users_id_seq"'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    226    225                      0    16471    roles 
   TABLE DATA           )   COPY public.roles (id, name) FROM stdin;
    public          postgres    false    215   SJ                  0    16475 	   schedules 
   TABLE DATA           _   COPY public.schedules (id, train_id, arrival_time, occupied_seats, departure_time) FROM stdin;
    public          postgres    false    217   �J       "          0    16479    stations 
   TABLE DATA           2   COPY public.stations (id, name, city) FROM stdin;
    public          postgres    false    219   �J       $          0    16483    tickets 
   TABLE DATA           H   COPY public.tickets (id, user_id, schedule_id, seat_number) FROM stdin;
    public          postgres    false    221   �J       &          0    16487    trains 
   TABLE DATA           �   COPY public.trains (id, departure_station_id, arrival_station_id, duration, seats, train_number, current_station_id) FROM stdin;
    public          postgres    false    223   �J       0          0    16791 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          postgres    false    233   K       (          0    16491    users 
   TABLE DATA           C   COPY public.users (id, email, password, name, surname) FROM stdin;
    public          postgres    false    225   %K       =           0    0    Roles_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Roles_id_seq"', 1, false);
          public          postgres    false    216            >           0    0    Schedules_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Schedules_id_seq"', 1, false);
          public          postgres    false    218            ?           0    0    Stations_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Stations_id_seq"', 1, false);
          public          postgres    false    220            @           0    0    Tickets_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Tickets_id_seq"', 1, false);
          public          postgres    false    222            A           0    0    Trains_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public."Trains_id_seq"', 1, false);
          public          postgres    false    224            B           0    0    Users_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Users_id_seq"', 1, false);
          public          postgres    false    226            C           0    0 	   roles_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.roles_seq', 151, true);
          public          postgres    false    227            D           0    0    schedules_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.schedules_seq', 1, false);
          public          postgres    false    229            E           0    0    stations_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.stations_seq', 1, false);
          public          postgres    false    230            F           0    0    tickets_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.tickets_seq', 1, false);
          public          postgres    false    231            G           0    0 
   trains_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.trains_seq', 1, false);
          public          postgres    false    232            H           0    0 	   users_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_seq', 1151, true);
          public          postgres    false    228            z           2606    16609    roles pk_rol_id 
   CONSTRAINT     M   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT pk_rol_id PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.roles DROP CONSTRAINT pk_rol_id;
       public            postgres    false    215            |           2606    24985    schedules pk_schedule_id 
   CONSTRAINT     V   ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT pk_schedule_id PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.schedules DROP CONSTRAINT pk_schedule_id;
       public            postgres    false    217            ~           2606    25006    stations pk_station_id 
   CONSTRAINT     T   ALTER TABLE ONLY public.stations
    ADD CONSTRAINT pk_station_id PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.stations DROP CONSTRAINT pk_station_id;
       public            postgres    false    219            �           2606    25023    tickets pk_ticket_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT pk_ticket_id PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.tickets DROP CONSTRAINT pk_ticket_id;
       public            postgres    false    221            �           2606    25048    trains pk_train_id 
   CONSTRAINT     P   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT pk_train_id PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.trains DROP CONSTRAINT pk_train_id;
       public            postgres    false    223            �           2606    25062    users pk_user_id 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_user_id PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT pk_user_id;
       public            postgres    false    225            �           2606    33176    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    233    233            �           2606    25012    trains fk_arrival_station_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT fk_arrival_station_id FOREIGN KEY (arrival_station_id) REFERENCES public.stations(id);
 F   ALTER TABLE ONLY public.trains DROP CONSTRAINT fk_arrival_station_id;
       public          postgres    false    223    4734    219            �           2606    25007    trains fk_departure_station_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT fk_departure_station_id FOREIGN KEY (departure_station_id) REFERENCES public.stations(id);
 H   ALTER TABLE ONLY public.trains DROP CONSTRAINT fk_departure_station_id;
       public          postgres    false    223    219    4734            �           2606    25028    tickets fk_schedule_id    FK CONSTRAINT     }   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fk_schedule_id FOREIGN KEY (schedule_id) REFERENCES public.schedules(id);
 @   ALTER TABLE ONLY public.tickets DROP CONSTRAINT fk_schedule_id;
       public          postgres    false    217    4732    221            �           2606    25049    schedules fk_train_id    FK CONSTRAINT     v   ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT fk_train_id FOREIGN KEY (train_id) REFERENCES public.trains(id);
 ?   ALTER TABLE ONLY public.schedules DROP CONSTRAINT fk_train_id;
       public          postgres    false    223    4738    217            �           2606    25068    tickets fk_user_id    FK CONSTRAINT     q   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 <   ALTER TABLE ONLY public.tickets DROP CONSTRAINT fk_user_id;
       public          postgres    false    4740    221    225            �           2606    25095 "   trains fkg6xwgk0hr7g2jvw2jn8378cdd    FK CONSTRAINT     �   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT fkg6xwgk0hr7g2jvw2jn8378cdd FOREIGN KEY (current_station_id) REFERENCES public.stations(id);
 L   ALTER TABLE ONLY public.trains DROP CONSTRAINT fkg6xwgk0hr7g2jvw2jn8378cdd;
       public          postgres    false    223    219    4734            �           2606    25087    user_roles role_id    FK CONSTRAINT     q   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES public.roles(id);
 <   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT role_id;
       public          postgres    false    215    233    4730            �           2606    25079    user_roles user_id    FK CONSTRAINT     q   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 <   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_id;
       public          postgres    false    4740    225    233               1   x�35�-.M,���25�3�8Sr3�,c΀��Լ��"�=... r/�             x������ � �      "      x������ � �      $      x������ � �      &      x������ � �      0      x������ � �      (      x������ � �     