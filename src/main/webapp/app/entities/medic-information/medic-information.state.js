(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medic-information', {
            parent: 'entity',
            url: '/medic-information',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medic_Information.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-information/medic-informations.html',
                    controller: 'Medic_InformationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medic_Information');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medic-information-detail', {
            parent: 'medic-information',
            url: '/medic-information/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.medic_Information.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medic-information/medic-information-detail.html',
                    controller: 'Medic_InformationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medic_Information');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medic_Information', function($stateParams, Medic_Information) {
                    return Medic_Information.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medic-information',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medic-information-detail.edit', {
            parent: 'medic-information-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-information/medic-information-dialog.html',
                    controller: 'Medic_InformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medic_Information', function(Medic_Information) {
                            return Medic_Information.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-information.new', {
            parent: 'medic-information',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-information/medic-information-dialog.html',
                    controller: 'Medic_InformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cp: null,
                                street: null,
                                extnumber: null,
                                intnumber: null,
                                phone1: null,
                                phone2: null,
                                email1: null,
                                email2: null,
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                snapchat: null,
                                linkedin: null,
                                vine: null,
                                recomended: null,
                                cvuconacyt: null,
                                cedula: null,
                                cedulaesp: null,
                                curp: null,
                                rfc: null,
                                birthday: null,
                                externalhospitals: null,
                                subid: null,
                                highid: null,
                                medicalinsurance: null,
                                socialinsurance: null,
                                responsibilityinsurance: null,
                                sINconacyt: null,
                                medicalinnumber: null,
                                socialinnumber: null,
                                responsibilityinnumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medic-information', null, { reload: 'medic-information' });
                }, function() {
                    $state.go('medic-information');
                });
            }]
        })
        .state('medic-information.edit', {
            parent: 'medic-information',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-information/medic-information-dialog.html',
                    controller: 'Medic_InformationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medic_Information', function(Medic_Information) {
                            return Medic_Information.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-information', null, { reload: 'medic-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medic-information.delete', {
            parent: 'medic-information',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medic-information/medic-information-delete-dialog.html',
                    controller: 'Medic_InformationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medic_Information', function(Medic_Information) {
                            return Medic_Information.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medic-information', null, { reload: 'medic-information' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
