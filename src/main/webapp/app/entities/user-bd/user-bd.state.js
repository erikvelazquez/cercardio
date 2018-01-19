(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-bd', {
            parent: 'entity',
            url: '/user-bd',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.userBD.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-bd/user-bds.html',
                    controller: 'UserBDController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userBD');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-bd-detail', {
            parent: 'user-bd',
            url: '/user-bd/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.userBD.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-bd/user-bd-detail.html',
                    controller: 'UserBDDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userBD');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserBD', function($stateParams, UserBD) {
                    return UserBD.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-bd',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-bd-detail.edit', {
            parent: 'user-bd-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-bd/user-bd-dialog.html',
                    controller: 'UserBDDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserBD', function(UserBD) {
                            return UserBD.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-bd.new', {
            parent: 'user-bd',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-bd/user-bd-dialog.html',
                    controller: 'UserBDDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                name: null,
                                lastname: null,
                                email: null,
                                password: null,
                                imagen: null,
                                isactive: null,
                                createdat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-bd', null, { reload: 'user-bd' });
                }, function() {
                    $state.go('user-bd');
                });
            }]
        })
        .state('user-bd.edit', {
            parent: 'user-bd',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-bd/user-bd-dialog.html',
                    controller: 'UserBDDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserBD', function(UserBD) {
                            return UserBD.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-bd', null, { reload: 'user-bd' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-bd.delete', {
            parent: 'user-bd',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-bd/user-bd-delete-dialog.html',
                    controller: 'UserBDDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserBD', function(UserBD) {
                            return UserBD.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-bd', null, { reload: 'user-bd' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
