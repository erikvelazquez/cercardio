(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('nurse', {
            parent: 'entity',
            url: '/nurse',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.nurse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nurse/nurses.html',
                    controller: 'NurseController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nurse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('nurse-detail', {
            parent: 'nurse',
            url: '/nurse/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.nurse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nurse/nurse-detail.html',
                    controller: 'NurseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('nurse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Nurse', function($stateParams, Nurse) {
                    return Nurse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'nurse',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('nurse-detail.edit', {
            parent: 'nurse-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nurse/nurse-dialog.html',
                    controller: 'NurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Nurse', function(Nurse) {
                            return Nurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nurse.new', {
            parent: 'nurse',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nurse/nurse-dialog.html',
                    controller: 'NurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cpid: null,
                                colony: null,
                                street: null,
                                streetnumber: null,
                                suitnumber: null,
                                phonenumber1: null,
                                phonenumber2: null,
                                email1: null,
                                email2: null,
                                facebook: null,
                                twitter: null,
                                instagram: null,
                                snapchat: null,
                                linkedin: null,
                                vine: null,
                                createdat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('nurse', null, { reload: 'nurse' });
                }, function() {
                    $state.go('nurse');
                });
            }]
        })
        .state('nurse.edit', {
            parent: 'nurse',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nurse/nurse-dialog.html',
                    controller: 'NurseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Nurse', function(Nurse) {
                            return Nurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nurse', null, { reload: 'nurse' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nurse.delete', {
            parent: 'nurse',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nurse/nurse-delete-dialog.html',
                    controller: 'NurseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Nurse', function(Nurse) {
                            return Nurse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nurse', null, { reload: 'nurse' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
