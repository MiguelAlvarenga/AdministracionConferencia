CREATE OR REPLACE FUNCTION fnInsertarUsuarioxPorgrama()
		RETURNS TRIGGER AS $usuarioxprograma$
		BEGIN
			INSERT INTO "public"."usuario_x_programa" ("id_programa", "id_responsable", "activo") VALUES (NEW.id_programa, NEW.id_usuario, NEW.activo);
			RETURN NEW;
		END;
		$usuarioxprograma$ LANGUAGE plpgsql;
		
CREATE TRIGGER tInsertarUsuarioxPrograma
	AFTER INSERT
	ON programa
	FOR EACH ROW
	EXECUTE PROCEDURE fnInsertarUsuarioxPorgrama();