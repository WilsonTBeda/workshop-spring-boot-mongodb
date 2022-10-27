package com.wilsonbeda.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.wilsonbeda.workshopmongo.domain.Post;
import com.wilsonbeda.workshopmongo.domain.User;
import com.wilsonbeda.workshopmongo.dto.AuthorDTO;
import com.wilsonbeda.workshopmongo.dto.CommentDTO;
import com.wilsonbeda.workshopmongo.repository.PostRepository;
import com.wilsonbeda.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("27/10/2022"), "Partiu codar","Nelio alves", new AuthorDTO(maria) ); 
		Post post2 = new Post(null, sdf.parse("27/10/2022"), "Partiu codar mais ainda","Nelio alves", new AuthorDTO(maria) ); 
	
		CommentDTO c1 = new CommentDTO("Codando demais véi!",sdf.parse("27/10/2022"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Codando muito véi!",sdf.parse("24/10/2022"), new AuthorDTO(alex));
		CommentDTO c3 = new CommentDTO("Codando pouco véi!",sdf.parse("27/10/2022"), new AuthorDTO(maria));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);	}

}
