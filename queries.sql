-- given (check in, check out, view, bed type) list available rooms
-- all attributes with a 'requested' prefix should be replaced in the java level
SELECT room_code, ocean_view, bed_type
FROM rooms
WHERE room_code NOT IN
   (SELECT DISTINCT room AS reserved_rooms
   FROM reservations
   WHERE view = requested_view
   AND bed_type = requested_bed_type
   AND ((check_in BETWEEN requested_check_in AND requested_check_out)
   OR (check_out BETWEEN requested_check_in AND requested_check_out)))
ORDER BY room_code;