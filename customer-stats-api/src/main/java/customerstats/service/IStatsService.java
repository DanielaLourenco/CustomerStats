package customerstats.service;

import org.springframework.data.jpa.repository.JpaRepository;

import customerstats.model.HourlyStats;

public interface IStatsService extends JpaRepository<HourlyStats, Integer>{

}
