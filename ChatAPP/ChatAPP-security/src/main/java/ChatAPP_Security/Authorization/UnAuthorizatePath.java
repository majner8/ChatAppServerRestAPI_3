package ChatAPP_Security.Authorization;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

public interface UnAuthorizatePath {

	public String[] getUnAuthorizatedPath();
	@Primary
	@Component
	public static class MainDefinePathToSkip implements UnAuthorizatePath{

		private String[]pathToSkip;
		@Autowired
		public MainDefinePathToSkip(List<UnAuthorizatePath> list) {
			list=Collections.synchronizedList(list);
			  list = list.stream()
                      .filter(manager -> !(manager instanceof MainDefinePathToSkip))
                      .collect(Collectors.toList());
			  this.pathToSkip= list
						.stream()
						.flatMap((element)->{
							return Arrays.stream(element.getUnAuthorizatedPath());
						}).toList()
						.toArray(String[]::new);
		}
		@Override
		public String[] getUnAuthorizatedPath() {
			return this.pathToSkip;

	}
}
}
