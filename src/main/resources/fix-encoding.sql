DROP PROCEDURE IF EXISTS fix_encoding ^;
CREATE PROCEDURE fix_encoding()
BEGIN
    DECLARE alterSQL TEXT DEFAULT '';
    DECLARE done BIT;
    DECLARE encodingCursor CURSOR FOR
        SELECT
            CONCAT(
                'ALTER TABLE ',
                tbl.`TABLE_SCHEMA`,
                '.',
                `tbl`.`TABLE_NAME`,
                ' CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;'
            )
        FROM
            information_schema.TABLES tbl
        WHERE
            tbl.TABLE_SCHEMA = 'dbname6';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN encodingCursor;

    SET FOREIGN_KEY_CHECKS = 0;

    read_loop:
        LOOP
            FETCH encodingCursor INTO alterSQL;
            IF done THEN
                LEAVE read_loop;
            END IF;
            SELECT alterSQL INTO @alterSQL;
            PREPARE stmt FROM @alterSQL;
            EXECUTE stmt;
        END LOOP read_loop;

    CLOSE encodingCursor;

    SET FOREIGN_KEY_CHECKS = 1;
END ^;
CALL fix_encoding ^;
DROP PROCEDURE fix_encoding ^;

