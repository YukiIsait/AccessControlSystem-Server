package tech.youko.acms.service.implementation

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import tech.youko.acms.entity.AccessInfoEntity
import tech.youko.acms.entity.id.AccessInfoId
import tech.youko.acms.repository.IAccessInfoRepository
import tech.youko.acms.service.IAccessInfoService

@Service
class AccessInfoService(private val accessInfoRepository: IAccessInfoRepository) : IAccessInfoService {
    override fun getAccessInfoById(id: AccessInfoId): AccessInfoEntity {
        return accessInfoRepository.findById(id).orElseThrow {
            EntityNotFoundException("AccessInfo '$id' not found")
        }
    }

    override fun listAccessInfoWithPage(page: Int, size: Int, sort: Sort): Page<AccessInfoEntity> {
        return accessInfoRepository.findAll(PageRequest.of(page, size, sort))
    }

    override fun listAccessInfoWithPageLike(page: Int, size: Int, sort: Sort, accessInfo: AccessInfoEntity): Page<AccessInfoEntity> {
        val exampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        return accessInfoRepository.findAll(Example.of(accessInfo, exampleMatcher), PageRequest.of(page, size, sort))
    }

    override fun existAccessInfoById(id: AccessInfoId): Boolean {
        return accessInfoRepository.existsById(id)
    }

    override fun addAccessInfo(accessInfo: AccessInfoEntity) {
        if (accessInfoRepository.existsById(accessInfo.id)) {
            throw EntityNotFoundException("AccessInfo '${accessInfo.id}' already exists")
        }
        accessInfoRepository.save(accessInfo)
    }

    override fun deleteAccessInfoById(id: AccessInfoId) {
        if (!accessInfoRepository.existsById(id)) {
            throw EntityNotFoundException("AccessInfo '$id' not found")
        }
        accessInfoRepository.deleteById(id)
    }

    override fun updateAccessInfo(accessInfo: AccessInfoEntity) {
        if (!accessInfoRepository.existsById(accessInfo.id)) {
            throw EntityNotFoundException("AccessInfo '${accessInfo.id}' not found")
        }
        accessInfoRepository.save(accessInfo)
    }
}
