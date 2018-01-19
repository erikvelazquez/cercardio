package mycercardiopackege.jh.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.AcademicDegree.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Appreciation.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Background.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.BloodGroup.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.CivilStatus.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Disabilities.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.DrugAddiction.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.EthnicGroup.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Gender.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.LivingPlace.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medic.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.MedicalAnalysis.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.MedicNurse.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.MedicNurse.class.getName() + ".nurses", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.MedicPacient.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.MedicPacient.class.getName() + ".pacients", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Nurse.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Occupation.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Pacient.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientApnp.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientApp.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientContact.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientGenerals.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientLaboratoy.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientMedicalData.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PrivateHealthInsurance.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Religion.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Reservation.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.SocialHealthInsurance.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.SocioeconomicLevel.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Status.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Time.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.UserBD.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Gender.class.getName() + ".medics", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medic.class.getName() + ".medics", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Nurse.class.getName() + ".genders", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Gender.class.getName() + ".genders", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Category.class.getName() + ".medics", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Gender.class.getName() + ".nurses", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medic.class.getName() + ".nurses", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medic.class.getName() + ".pacients", jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Diagnosis.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Domicile.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Indigenous_Languages.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medical_Procedures.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medicine.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Medic_Information.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Programs.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Type_Program.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Way_of_Administration.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.PacientMedicalAnalysis.class.getName(), jcacheConfiguration);
            cm.createCache(mycercardiopackege.jh.domain.Timers.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
