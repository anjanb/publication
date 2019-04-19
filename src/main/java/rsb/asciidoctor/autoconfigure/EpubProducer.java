package rsb.asciidoctor.autoconfigure;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.springframework.stereotype.Component;

import java.io.File;

@Log4j2
@RequiredArgsConstructor
class EpubProducer implements DocumentProducer {

	private final PublicationProperties properties;

	@Override
	public File[] produce(Asciidoctor asciidoctor) {
		AttributesBuilder attributesBuilder = this.buildCommonAttributes(
				this.properties.getBookName(), this.properties.getEpub().getIsbn(),
				this.properties.getCode());
		OptionsBuilder optionsBuilder = this.buildCommonOptions("epub3",
				attributesBuilder);
		File index = this.getIndexAdoc(this.properties.getRoot());
		asciidoctor.convertFile(index, optionsBuilder);
		return new File[] { new File(index.getParentFile(), "index.epub") };
	}

}
