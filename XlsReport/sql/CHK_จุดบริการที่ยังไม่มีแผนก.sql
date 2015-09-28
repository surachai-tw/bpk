SELECT "Code Service Point", "Service Point Name" FROM 
(
    SELECT 
    base_service_point.base_service_point_id "Code Service Point", 
    base_service_point.description "Service Point Name", 
    base_department.base_department_id "Code Department", 
    base_department.description "Department Name"
    FROM base_service_point 
    LEFT JOIN base_department ON base_service_point.base_department_id=base_department.base_department_id 
) tmp 
WHERE tmp."Code Department" IS NULL 
ORDER BY "Service Point Name" COLLATE "th_TH" 
