PGDMP      %            
    {            RAILWAY_TRANSPORT_COMPANY    16.0    16.0 C    0           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            1           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            2           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            3           1262    24681    RAILWAY_TRANSPORT_COMPANY    DATABASE     �   CREATE DATABASE "RAILWAY_TRANSPORT_COMPANY" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
 +   DROP DATABASE "RAILWAY_TRANSPORT_COMPANY";
                postgres    false            �            1259    24682    roles    TABLE     ^   CREATE TABLE public.roles (
    id bigint NOT NULL,
    rol character varying(45) NOT NULL
);
    DROP TABLE public.roles;
       public         heap    postgres    false            �            1259    24685    Roles_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Roles_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."Roles_id_seq";
       public          postgres    false    215            4           0    0    Roles_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Roles_id_seq" OWNED BY public.roles.id;
          public          postgres    false    216            �            1259    24686 	   schedules    TABLE     �   CREATE TABLE public.schedules (
    id integer NOT NULL,
    departure_time timestamp without time zone NOT NULL,
    arrival_time timestamp without time zone NOT NULL,
    train_id integer NOT NULL,
    occupied_seats integer
);
    DROP TABLE public.schedules;
       public         heap    postgres    false            �            1259    24689    Schedules_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Schedules_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."Schedules_id_seq";
       public          postgres    false    217            5           0    0    Schedules_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."Schedules_id_seq" OWNED BY public.schedules.id;
          public          postgres    false    218            �            1259    24690    stations    TABLE     �   CREATE TABLE public.stations (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    city character varying(45) NOT NULL
);
    DROP TABLE public.stations;
       public         heap    postgres    false            �            1259    24693    Stations_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Stations_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."Stations_id_seq";
       public          postgres    false    219            6           0    0    Stations_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public."Stations_id_seq" OWNED BY public.stations.id;
          public          postgres    false    220            �            1259    24694    tickets    TABLE     �   CREATE TABLE public.tickets (
    id integer NOT NULL,
    seat_number character varying(255) NOT NULL,
    user_id integer NOT NULL,
    schedule_id integer NOT NULL
);
    DROP TABLE public.tickets;
       public         heap    postgres    false            �            1259    24697    Tickets_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Tickets_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."Tickets_id_seq";
       public          postgres    false    221            7           0    0    Tickets_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public."Tickets_id_seq" OWNED BY public.tickets.id;
          public          postgres    false    222            �            1259    24698    trains    TABLE     �   CREATE TABLE public.trains (
    id integer NOT NULL,
    departure_station_id integer NOT NULL,
    arrival_station_id integer NOT NULL,
    train_number character varying,
    duration interval,
    seats integer
);
    DROP TABLE public.trains;
       public         heap    postgres    false            �            1259    24701    Trains_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Trains_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public."Trains_id_seq";
       public          postgres    false    223            8           0    0    Trains_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public."Trains_id_seq" OWNED BY public.trains.id;
          public          postgres    false    224            �            1259    24702    users    TABLE     �   CREATE TABLE public.users (
    id integer NOT NULL,
    name character varying(45) NOT NULL,
    surname character varying(45) NOT NULL,
    email character varying(45),
    password character varying
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    24705    Users_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Users_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."Users_id_seq";
       public          postgres    false    225            9           0    0    Users_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public."Users_id_seq" OWNED BY public.users.id;
          public          postgres    false    226            �            1259    24706 	   roles_seq    SEQUENCE     s   CREATE SEQUENCE public.roles_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.roles_seq;
       public          postgres    false            �            1259    24707    schedules_seq    SEQUENCE     w   CREATE SEQUENCE public.schedules_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.schedules_seq;
       public          postgres    false            �            1259    24708    stations_seq    SEQUENCE     v   CREATE SEQUENCE public.stations_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.stations_seq;
       public          postgres    false            �            1259    24709    tickets_seq    SEQUENCE     u   CREATE SEQUENCE public.tickets_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.tickets_seq;
       public          postgres    false            �            1259    24710 
   trains_seq    SEQUENCE     t   CREATE SEQUENCE public.trains_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 !   DROP SEQUENCE public.trains_seq;
       public          postgres    false            �            1259    24781 
   user_roles    TABLE     _   CREATE TABLE public.user_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            �            1259    24711 	   users_seq    SEQUENCE     s   CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.users_seq;
       public          postgres    false            s           2604    24712    roles id    DEFAULT     f   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public."Roles_id_seq"'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    216    215            t           2604    24713    schedules id    DEFAULT     n   ALTER TABLE ONLY public.schedules ALTER COLUMN id SET DEFAULT nextval('public."Schedules_id_seq"'::regclass);
 ;   ALTER TABLE public.schedules ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217            u           2604    24714    stations id    DEFAULT     l   ALTER TABLE ONLY public.stations ALTER COLUMN id SET DEFAULT nextval('public."Stations_id_seq"'::regclass);
 :   ALTER TABLE public.stations ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    220    219            v           2604    24715 
   tickets id    DEFAULT     j   ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public."Tickets_id_seq"'::regclass);
 9   ALTER TABLE public.tickets ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    222    221            w           2604    24716 	   trains id    DEFAULT     h   ALTER TABLE ONLY public.trains ALTER COLUMN id SET DEFAULT nextval('public."Trains_id_seq"'::regclass);
 8   ALTER TABLE public.trains ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    224    223            x           2604    24717    users id    DEFAULT     f   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public."Users_id_seq"'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    226    225                      0    24682    roles 
   TABLE DATA           (   COPY public.roles (id, rol) FROM stdin;
    public          postgres    false    215   %G                 0    24686 	   schedules 
   TABLE DATA           _   COPY public.schedules (id, departure_time, arrival_time, train_id, occupied_seats) FROM stdin;
    public          postgres    false    217   fG                 0    24690    stations 
   TABLE DATA           2   COPY public.stations (id, name, city) FROM stdin;
    public          postgres    false    219   �G       !          0    24694    tickets 
   TABLE DATA           H   COPY public.tickets (id, seat_number, user_id, schedule_id) FROM stdin;
    public          postgres    false    221   �G       #          0    24698    trains 
   TABLE DATA           m   COPY public.trains (id, departure_station_id, arrival_station_id, train_number, duration, seats) FROM stdin;
    public          postgres    false    223   �G       -          0    24781 
   user_roles 
   TABLE DATA           6   COPY public.user_roles (user_id, role_id) FROM stdin;
    public          postgres    false    233   �G       %          0    24702    users 
   TABLE DATA           C   COPY public.users (id, name, surname, email, password) FROM stdin;
    public          postgres    false    225   �G       :           0    0    Roles_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Roles_id_seq"', 1, false);
          public          postgres    false    216            ;           0    0    Schedules_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public."Schedules_id_seq"', 1, false);
          public          postgres    false    218            <           0    0    Stations_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Stations_id_seq"', 1, false);
          public          postgres    false    220            =           0    0    Tickets_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public."Tickets_id_seq"', 1, false);
          public          postgres    false    222            >           0    0    Trains_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public."Trains_id_seq"', 1, false);
          public          postgres    false    224            ?           0    0    Users_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public."Users_id_seq"', 1, false);
          public          postgres    false    226            @           0    0 	   roles_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.roles_seq', 151, true);
          public          postgres    false    227            A           0    0    schedules_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.schedules_seq', 1, false);
          public          postgres    false    228            B           0    0    stations_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.stations_seq', 1, false);
          public          postgres    false    229            C           0    0    tickets_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.tickets_seq', 1, false);
          public          postgres    false    230            D           0    0 
   trains_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.trains_seq', 1, false);
          public          postgres    false    231            E           0    0 	   users_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_seq', 1151, true);
          public          postgres    false    232            z           2606    24719    roles pk_rol_id 
   CONSTRAINT     M   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT pk_rol_id PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.roles DROP CONSTRAINT pk_rol_id;
       public            postgres    false    215            |           2606    24721    schedules pk_schedule_id 
   CONSTRAINT     V   ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT pk_schedule_id PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.schedules DROP CONSTRAINT pk_schedule_id;
       public            postgres    false    217            ~           2606    24723    stations pk_station_id 
   CONSTRAINT     T   ALTER TABLE ONLY public.stations
    ADD CONSTRAINT pk_station_id PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.stations DROP CONSTRAINT pk_station_id;
       public            postgres    false    219            �           2606    24725    tickets pk_ticket_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT pk_ticket_id PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.tickets DROP CONSTRAINT pk_ticket_id;
       public            postgres    false    221            �           2606    24727    trains pk_train_id 
   CONSTRAINT     P   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT pk_train_id PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.trains DROP CONSTRAINT pk_train_id;
       public            postgres    false    223            �           2606    24729    users pk_user_id 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT pk_user_id PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT pk_user_id;
       public            postgres    false    225            �           2606    24776    trains fk_arrival_station_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT fk_arrival_station_id FOREIGN KEY (arrival_station_id) REFERENCES public.stations(id);
 F   ALTER TABLE ONLY public.trains DROP CONSTRAINT fk_arrival_station_id;
       public          postgres    false    223    219    4734            �           2606    24735    trains fk_departure_station_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.trains
    ADD CONSTRAINT fk_departure_station_id FOREIGN KEY (departure_station_id) REFERENCES public.stations(id);
 H   ALTER TABLE ONLY public.trains DROP CONSTRAINT fk_departure_station_id;
       public          postgres    false    4734    223    219            �           2606    24789    user_roles fk_role_id    FK CONSTRAINT     t   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES public.roles(id);
 ?   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fk_role_id;
       public          postgres    false    233    215    4730            �           2606    24745    tickets fk_schedule_id    FK CONSTRAINT     }   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fk_schedule_id FOREIGN KEY (schedule_id) REFERENCES public.schedules(id);
 @   ALTER TABLE ONLY public.tickets DROP CONSTRAINT fk_schedule_id;
       public          postgres    false    4732    221    217            �           2606    24750    schedules fk_train_id    FK CONSTRAINT     v   ALTER TABLE ONLY public.schedules
    ADD CONSTRAINT fk_train_id FOREIGN KEY (train_id) REFERENCES public.trains(id);
 ?   ALTER TABLE ONLY public.schedules DROP CONSTRAINT fk_train_id;
       public          postgres    false    223    217    4738            �           2606    24755    tickets fk_user_id    FK CONSTRAINT     q   ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 <   ALTER TABLE ONLY public.tickets DROP CONSTRAINT fk_user_id;
       public          postgres    false    225    221    4740            �           2606    24784    user_roles fk_user_id    FK CONSTRAINT     t   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(id);
 ?   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fk_user_id;
       public          postgres    false    225    233    4740               1   x�35�-.M,���25�3�8Sr3�,c΀��Լ��"�=... r/�            x������ � �            x������ � �      !      x������ � �      #      x������ � �      -      x������ � �      %   E   x��45�t�+�����tNLJ�����".C#N���<N�ÛsS�`@a�Ģ�DN���`� <T}     