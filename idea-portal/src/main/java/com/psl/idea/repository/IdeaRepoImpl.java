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
		String hql="FROM Idea I where I.user.userId=:id";
		Query<Idea> query=session.createQuery(hql, Idea.class);
		query.setParameter("id", userId);
		List<Idea> ans=query.list();
		session.close();
		return ans;
	}
	public List<Idea> findbylike(long themeID) {
		Session session=sessionFactory.openSession();
		String hql="FROM Idea I where I.theme.themeId=:id ORDER BY I.rating DESC";
		Query<Idea> query=session.createQuery(hql, Idea.class);
		query.setParameter("id", themeID);
		List<Idea> ans=query.list();
		session.close();
		return ans;
	}
	public List<Idea> findbydate(long themeID) {
		Session session=sessionFactory.openSession();
		String hql="FROM Idea I where I.theme.themeId=:id ORDER BY I.ideaId DESC";
		Query<Idea> query=session.createQuery(hql, Idea.class);	
		query.setParameter("id", themeID);
		List<Idea> ans=query.list();
		session.close();
		return ans;
	}
//	public List<Idea> findbycomment(long themeID) {
//		// this function isn't working fine
//		Session session=sessionFactory.openSession();
//		String hql="FROM Idea I where I.theme.themeId=:id LEFT OUTER JOIN FROM Comment C WHERE C.theme.themeId=:id1 GROUP BY C.idea.ideaId ON I.theme=C.theme ORDER BY COUNT(C.commentId) DESC";
//		Query<Idea> query=session.createQuery(hql, Idea.class);
//		query.setParameter("id", themeID);
//		query.setParameter("id1", themeID);
//		List<Idea> ans=query.list();
//		session.close();
//		return ans;
//	}
	public List<Idea> findAll(long themeID) {
		Session session=sessionFactory.openSession();
		String hql="FROM Idea I where I.theme.themeId=:id";
		Query<Idea> query=session.createQuery(hql, Idea.class);
		query.setParameter("id", themeID);
		List<Idea> ans=query.list();
		session.close();
		return ans;
	}
	
}
