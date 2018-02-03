(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pacient', {
            parent: 'entity',
            url: '/pacient',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacient.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient/pacients.html',
                    controller: 'PacientController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacient');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pacient-detail', {
            parent: 'pacient',
            url: '/pacient/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.pacient.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pacient/pacient-detail.html',
                    controller: 'PacientDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pacient');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Pacient', function($stateParams, Pacient) {
                    return Pacient.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pacient',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pacient-detail.edit', {
            parent: 'pacient-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient/pacient-dialog.html',
                    controller: 'PacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pacient', function(Pacient) {
                            return Pacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient.new', {
            parent: 'pacient',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient/pacient-dialog.html',
                    controller: 'PacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cp: null,
                                colony: null,
                                street: null,
                                streetnumber: null,
                                suitnumber: null,
                                phonenumber1: null,
                                phonenumber2: null,
                                email1: null,
                                email2: null,
                                curp: null,
                                rfc: null,
                                dateofbirth: null,
                                placeofbirth: null,
                                privatenumber: null,
                                socialnumber: null,
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                snapchat: null,
                                linkedin: null,
                                vine: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pacient', null, { reload: 'pacient' });
                }, function() {
                    $state.go('pacient');
                });
            }]
        })
        .state('pacient.edit', {
            parent: 'pacient',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient/pacient-dialog.html',
                    controller: 'PacientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pacient', function(Pacient) {
                            return Pacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient', null, { reload: 'pacient' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pacient.delete', {
            parent: 'pacient',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pacient/pacient-delete-dialog.html',
                    controller: 'PacientDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pacient', function(Pacient) {
                            return Pacient.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pacient', null, { reload: 'pacient' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
