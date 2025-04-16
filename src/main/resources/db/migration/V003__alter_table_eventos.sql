alter table eventos add column totalIngressos int null;
alter table eventos add column ingressosDisponiveis int null;
update eventos set totalIngressos = 10, ingressosDisponiveis = 10;