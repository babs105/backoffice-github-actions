INSERT INTO user (id,created_by,created_date,modified_by,modified_date,deleted,firstname,lastname,password,username) VALUES (6,NULL,NULL,NULL,NULL,false,'babacar','Dieng','$2a$10$8nIY5HORjK0zjhaTBCPVH.Oz0KqwW.iLtipMCZPqwqEgdz/bTmfF2','babacar.dieng41@gmail.com');

INSERT INTO role (id,created_by,created_date,modified_by,modified_date,deleted,description,name) VALUES (3,NULL,NULL,NULL,NULL,false,'access user','USER_TRACE') ;

INSERT INTO user_role (user_id,role_id) VALUES (6,3);

INSERT INTO evenement (id,date_event,dateheure_depose_balise,dateheure_pose_balise,emis_par,heure_appelopc,heure_appelpat,heure_arriveepat,heure_debut_event,mat_vehicule,nompat,pk_debut_balisage,pk_event,pk_fin_balisage,secteur,sens,statut_event,voie,cause_event,nature_event,type_balisage,type_vehicule,etat_event,statut_rom_event,observation,created_by,created_date,modified_by,modified_date,deleted,deleted_by,deleted_at) VALUES(8, '2022-12-01','2022-12-03 15:11','2022-12-01 20:07','PAT','20:07','NON','20:07','20:07','AA 670 BX','oumar ly','23+450','A1 30+400','32+450','AMT','SENS 1','remorque','BAU','CARBURANT','PANNE','URGENCE','C2','Terminer','remorque',NULL,NULL,NULL,NULL, NULL,false,NULL,NULL);



