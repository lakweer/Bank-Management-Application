DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `random_string`(length SMALLINT(3), seed VARCHAR(255)) RETURNS varchar(255) CHARSET utf8
    NO SQL
BEGIN
    SET @output = '';

    IF seed IS NULL OR seed = '' THEN SET seed = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'; END IF;

    SET @rnd_multiplier = LENGTH(seed);

    WHILE LENGTH(@output) < length DO
        # Select random character and add to output
        SET @output = CONCAT(@output, SUBSTRING(seed, RAND() * (@rnd_multiplier + 1), 1));
    END WHILE;

    RETURN @output;
END$$
DELIMITER ;