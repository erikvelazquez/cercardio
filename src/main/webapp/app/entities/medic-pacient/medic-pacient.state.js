(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medic-pacient', {
            parent: 'entity',
            url: '/medic-pacient',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicPacient.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-pacient/medic-pacients.html',
                    controller: 'MedicPacientController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicPacient');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medic-pacient-detail', {
            parent: 'medic-pacient',
            url: '/medic-pacient/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medicPacient.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-pacient/medic-pacient-detail.html',
                    controller: 'MedicPacientDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicPacient');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MedicPacient', function($stateParams, MedicPacient) {
                    return MedicPacient.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medic-pacient',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medic-pacient-detail.edit', {
            parent: 'medic-pacient-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-pacient/medic-pacient-dialog.html',
                    controller: 'MedicPacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicPacient', function(MedicPacient) {
                            return MedicPacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-pacient.new', {
            parent: 'medic-pacient',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-pacient/medic-pacient-dialog.html',
                    controller: 'MedicPacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medic-pacient', null, { reload: 'medic-pacient' });
                }, function() {
                    $state.go('medic-pacient');
                });
            }]
        })
        .state('medic-pacient.edit', {
            parent: 'medic-pacient',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-pacient/medic-pacient-dialog.html',
                    controller: 'MedicPacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicPacient', function(MedicPacient) {
                            return MedicPacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-pacient', null, { reload: 'medic-pacient' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-pacient.delete', {
            parent: 'medic-pacient',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-pacient/medic-pacient-delete-dialog.html',
                    controller: 'MedicPacientDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MedicPacient', function(MedicPacient) {
                            return MedicPacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-pacient', null, { reload: 'medic-pacient' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
