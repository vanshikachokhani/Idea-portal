package com.psl.idea.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.psl.idea.models.Idea;

@Repository
public class IdeaRepoImpl {
    @Autowired
	SessionFactory sessionFactory;
	public List<Idea> findbyUserUserId(long userId){
		Session session=sessionFactory.openSession();
		String hql="SELECT * FROM Idea I where I.user_id=:id";
		Query query=session.createQuery(hql);
		query.setParameter("id", userId);
		List ans=query.list();
		session.close();
		return ans;
	}
	public List<Idea> findbylike() {
		Session session=sessionFactory.openSession();
		String hql="SELECT * FROM Idea I ORDER BY I.rating ASC";
		Query query=session.createQuery(hql);
		List ans=query.list();
		session.close();
		return ans;
	}
	public List<Idea> findbydate() {
		Session session=sessionFactory.openSession();
		String hql="SELECT * FROM Idea I ORDER BY I.date ASC";
		Query query=session.createQuery(hql);	
		List ans=query.list();
		session.close();
		return ans;
	}
	public List<Idea> findbycomment() {
		Session session=sessionFactory.openSession();
		String hql="SELECT * FROM Idea I ORDER BY I.comment ASC";
		Query query=session.createQuery(hql);
		List ans=query.list();
		session.close();
		return ans;
	}
	
}
