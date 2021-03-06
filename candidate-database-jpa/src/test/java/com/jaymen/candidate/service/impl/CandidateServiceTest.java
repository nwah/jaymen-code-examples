/**
 * 
 */
package com.jaymen.candidate.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jaymen.candidate.dao.CandidateDao;
import com.jaymen.candidate.dao.exceptions.DaoStoreException;
import com.jaymen.candidate.dao.jpa.CandidateDaoJpaTest;
import com.jaymen.candidate.model.Candidate;
import com.jaymen.candidate.service.CandidateService;

/**
 * @author jeffxor
 *
 */

public class CandidateServiceTest {

	private CandidateDao dao;
	private CandidateService service;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {	
		dao = mock(CandidateDao.class);
		service = new CandidateServiceImp(dao);
	}

	@Test
	public void findAll(){
		List<Candidate> candidates = CandidateDaoJpaTest.createCandidates();

		when(dao.findAll()).thenReturn(candidates);

		Collection<Candidate> result = service.findAll();

		verify(dao).findAll();
		assertEquals(3, result.size());		
	}

	@Test
	public void findById(){
		Candidate candidate = CandidateDaoJpaTest.createCandidate(1, "Test Candidate One");

		when(dao.findById(1)).thenReturn(candidate);

		Candidate candidateTest = service.findById(1);

		verify(dao).findById(1);
		assertSame(candidate, candidateTest);
	}

	@Test
	public void findByUnknownId(){
		when(dao.findById(2)).thenReturn(null);

		Candidate result = service.findById(2);

		verify(dao).findById(2);
		assertNull(result);
	}
	
	@Test
	public void persist() throws DaoStoreException{
		
		Candidate candidate = CandidateDaoJpaTest.createCandidate(1, "test user");
				
		when(dao.persist(candidate)).thenReturn(candidate);
		
		Candidate actual = service.persist(candidate);

		verify(dao).persist(candidate);
		assertSame(candidate, actual);
	}
	
	@Test
	public void persistDaoStoreException() throws DaoStoreException{		
		Candidate candidate = CandidateDaoJpaTest.createCandidate(1, "test user");
		IllegalStateException illegalStateException = new IllegalStateException("");
				
		//Expectations
		doThrow(illegalStateException).when(dao).persist(candidate);
		
		try{
			//Test
			service.persist(candidate);
			fail("Should have thrown an IllegalStateException");
		}catch (Exception e) {
			assertSame(illegalStateException, e);
		}		
		verify(dao).persist(candidate);		
	}
	
	@Test
	public void remove() throws DaoStoreException{
		
		Candidate candidate = CandidateDaoJpaTest.createCandidate(1, "test user");
				
		doNothing().when(dao).remove(candidate);
		
		service.remove(candidate);

		verify(dao).remove(candidate);
	}
	
	@Test
	public void removeDaoStoreException() throws DaoStoreException{
		Candidate candidate = CandidateDaoJpaTest.createCandidate(1, "test user");
		IllegalStateException illegalStateException = new IllegalStateException("");
				
		doThrow(illegalStateException).when(dao).remove(candidate);

		try{
			service.remove(candidate);
			fail("Should have thrown an IllegalStateException");
		}catch (Exception e) {
			assertSame(illegalStateException, e);
		}		
		verify(dao).remove(candidate);		
	}
}
