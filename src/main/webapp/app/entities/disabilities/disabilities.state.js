(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('disabilities', {
            parent: 'entity',
            url: '/disabilities',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.disabilities.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disabilities/disabilities.html',
                    controller: 'DisabilitiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disabilities');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('disabilities-detail', {
            parent: 'disabilities',
            url: '/disabilities/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.disabilities.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/disabilities/disabilities-detail.html',
                    controller: 'DisabilitiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('disabilities');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Disabilities', function($stateParams, Disabilities) {
                    return Disabilities.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'disabilities',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('disabilities-detail.edit', {
            parent: 'disabilities-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disabilities/disabilities-dialog.html',
                    controller: 'DisabilitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disabilities', function(Disabilities) {
                            return Disabilities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disabilities.new', {
            parent: 'disabilities',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disabilities/disabilities-dialog.html',
                    controller: 'DisabilitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subgroup: null,
                                disability: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('disabilities', null, { reload: 'disabilities' });
                }, function() {
                    $state.go('disabilities');
                });
            }]
        })
        .state('disabilities.edit', {
            parent: 'disabilities',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disabilities/disabilities-dialog.html',
                    controller: 'DisabilitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Disabilities', function(Disabilities) {
                            return Disabilities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disabilities', null, { reload: 'disabilities' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('disabilities.delete', {
            parent: 'disabilities',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/disabilities/disabilities-delete-dialog.html',
                    controller: 'DisabilitiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Disabilities', function(Disabilities) {
                            return Disabilities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('disabilities', null, { reload: 'disabilities' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
