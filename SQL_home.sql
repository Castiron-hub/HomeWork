#1.  Вывести список сотрудников, получающих заработную плату большую чем у непосредственного руководителя. Отразить поля: имя, должность, id отдела, заработная плата сотрудника, зарплата
#руководителя

SELECT a.emp_name, a.job_name, a.dep_id, a.salary from  employees a, employees b
WHERE b.emp_id = a.manager_id and  a.salary > b.salary

 

#2.  Вывести список сотрудников, получающих максимальную заработную плату в своем отделе.
#Отразить поля: имя, должность, id отдела, заработная плата сотрудника


SELECT emp_name, job_name, dep_id, salary
FROM employees AS employees
WHERE employees.salary = (SELECT MAX(salary) FROM employees AS a WHERE a.dep_id = employees.dep_id);

 

# 3. Вывести список ID отделов, количество сотрудников в которых не превышает 3 человек

SELECT dep_id from  employees
GROUP BY dep_id
HAVING GOUNT(*) <= 3

#4.Вывести список сотрудников, не имеющих назначенного руководителя, работающего в том же
#отделе. Отразить поля: имя, должность, наименование отдела

SELECT a.emp_name, a.job_name, a.dep_id
FROM  employees a
LEFT JOIN employees b on (b.emp_id = a.manager_id and b.dep_id = a.dep_id)
WHERE a.manager_id is null
 

#5.  Ранжировать сотрудников в каждом отделе по стажу работы в днях до текущей даты
#(current_date) по убывающей. Отразить поля: имя, должность, id отдела, стаж в днях, ранг

SELECT emp_name, job_name, , dep_id , DATEDIFF(day, current_date, hire_date) as 'Stazh' from employees
ORDER BY Stazh ASC;
 

#6.  Определить количество сотрудников, относящихся к каждому уровню заработной платы, отсортировать по убыванию

В задании присутствует только 1 таблица employees, в которой нет поля/полей по которому можно определить уровень заработной платы
