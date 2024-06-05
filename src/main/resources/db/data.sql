truncate table users cascade;
truncate table media cascade;

insert into users(id,email,password, time_created) values
(200,'john@email.com', 'password','2024-06-04T15:03:03.792009700'),
(201,'john1@email.com', 'password','2024-06-04T15:03:03.792009700'),
(202,'john2@email.com', 'password','2024-06-04T15:03:03.792009700'),
(203,'john3@email.com', 'password','2024-06-04T15:03:03.792009700');


insert into media(id,category, description, url, time_created, uploader_Id) values
(100,'ACTION','media 1', 'https:www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700',200),
(101,'ROMANCE','media 2', 'https:www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700', 200),
(102,'STEP_MOM','media 3', 'https:www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700',200),
(103,'COMEDY','media 4', 'https:www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700',201),
(104,'SCI_FI','media 5', 'https:www.cloudinary.com/media1', '2024-06-04T15:03:03.792009700',201);
