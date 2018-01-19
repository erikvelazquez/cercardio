(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient-medical-data', {
            parent: 'entity',
            url: '/pacient-medical-data',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientMedicalData.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data.html',
                    controller: 'PacientMedicalDataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientMedicalData');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-medical-data-detail', {
            parent: 'pacient-medical-data',
            url: '/pacient-medical-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacientMedicalData.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data-detail.html',
                    controller: 'PacientMedicalDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacientMedicalData');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PacientMedicalData', function($stateParams, PacientMedicalData) {
                    return PacientMedicalData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient-medical-data',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-medical-data-detail.edit', {
            parent: 'pacient-medical-data-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data-dialog.html',
                    controller: 'PacientMedicalDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientMedicalData', function(PacientMedicalData) {
                            return PacientMedicalData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-medical-data.new', {
            parent: 'pacient-medical-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data-dialog.html',
                    controller: 'PacientMedicalDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                diseases: null,
                                surgicalinterventions: null,
                                useofdrugs: null,
                                allergies: null,
                                ahffather: null,
                                ahfmother: null,
                                ahfothers: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-data', null, { reload: 'pacient-medical-data' });
                }, function() {
                    $state.go('pacient-medical-data');
                });
            }]
        })
        .state('pacient-medical-data.edit', {
            parent: 'pacient-medical-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data-dialog.html',
                    controller: 'PacientMedicalDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PacientMedicalData', function(PacientMedicalData) {
                            return PacientMedicalData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-data', null, { reload: 'pacient-medical-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient-medical-data.delete', {
            parent: 'pacient-medical-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient-medical-data/pacient-medical-data-delete-dialog.html',
                    controller: 'PacientMedicalDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PacientMedicalData', function(PacientMedicalData) {
                            return PacientMedicalData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient-medical-data', null, { reload: 'pacient-medical-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
